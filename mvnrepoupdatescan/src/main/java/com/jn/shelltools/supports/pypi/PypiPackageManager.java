package com.jn.shelltools.supports.pypi;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.agileway.vfs.utils.FileObjects;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.Filter;
import com.jn.langx.annotation.NotEmpty;
import com.jn.langx.annotation.Nullable;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Emptys;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;
import com.jn.langx.util.Throwables;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.function.Predicate2;
import com.jn.langx.util.net.URLs;
import com.jn.langx.util.struct.Holder;
import com.jn.langx.util.struct.Pair;
import com.jn.langx.util.struct.pair.NameValuePair;
import com.jn.shelltools.core.LocalPackageScanner;
import com.jn.shelltools.core.PackageArtifact;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.pypi.dependency.ArtifactsDependenciesFinder;
import com.jn.shelltools.supports.pypi.dependency.DefaultArtifactsDependenciesFinder;
import com.jn.shelltools.supports.pypi.dependency.RequirementsArtifact;
import com.jn.shelltools.supports.pypi.packagemetadata.PipPackageMetadata;
import com.jn.shelltools.supports.pypi.packagemetadata.PipPackageRelease;
import com.jn.shelltools.supports.pypi.versionspecifier.VersionSpecifierParser;
import com.jn.shelltools.supports.pypi.versionspecifier.VersionSpecifiers;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.Selectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持特性：
 * <pre>
 *  1.  从Pypi下载包到本地仓库
 *  2.  在本地仓库进行包扫描
 * </pre>
 */
public class PypiPackageManager implements LocalPackageScanner {
    private static final Logger logger = LoggerFactory.getLogger(PypiPackageManager.class);

    private ArtifactManager artifactManager;
    private PypiPackageMetadataManager metadataManager;
    private ConcurrentHashMap<PackageGAV, Integer> downloading = new ConcurrentHashMap<>();

    public void setMetadataManager(PypiPackageMetadataManager metadataManager) {
        this.metadataManager = metadataManager;
    }


    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    public void downloadPackage(@NotEmpty String versionedPackageName, final boolean withDependencies, @Nullable String targetDirectory) {
        downloadPackage(versionedPackageName, withDependencies, targetDirectory, new ConcurrentHashMap<>());
    }

    /**
     * 下载至本地仓库
     *
     * @param versionedPackageName
     * @param withDependencies
     * @param targetDirectory
     */
    public boolean downloadPackage(@NotEmpty String versionedPackageName, final boolean withDependencies, @Nullable String targetDirectory, Map<String, Holder<List<PypiArtifact>>> finished) {
        String packageName = null;
        CommonExpressionBoundary versionBoundary = null;
        if (Strings.isBlank(versionedPackageName)) {
            logger.error("invalid package name {}", versionedPackageName);
            return false;
        }
        versionedPackageName = versionedPackageName.replaceAll("(.*)(;.*)", "$1");
        if (Strings.isBlank(versionedPackageName)) {
            logger.error("invalid package name {}", versionedPackageName);
            return false;
        }
        if (VersionSpecifiers.versionAbsent(versionedPackageName)) {
            packageName = versionedPackageName;
        } else {
            if (VersionSpecifiers.versionedPackageName(versionedPackageName)) {
                VersionSpecifierParser parser = new VersionSpecifierParser();
                NameValuePair<CommonExpressionBoundary> parsedResult = parser.parse(versionedPackageName);
                packageName = parsedResult.getName();
                versionBoundary = parsedResult.getValue();
            } else {
                logger.error("invalid package name {}", versionedPackageName);
                return false;
            }
        }
        PipPackageMetadata packageMetadata = null;
        if (Strings.isBlank(packageName)) {
            logger.error("invalid package name {}", versionedPackageName);
            return false;
        }
        try {
            packageMetadata = metadataManager.getOfficialMetadata(packageName);
        } catch (Throwable ex) {
            throw Throwables.wrapAsRuntimeException(ex);
        }
        if (packageMetadata == null) {
            logger.error(StringTemplates.formatWithPlaceholder("package ({}) is not exists", packageName));
            return false;
        }


        List<String> versions = selectVersions(packageMetadata, versionBoundary);
        logger.info("selected {} versions: {}", packageName, Strings.join(",", versions));

        PipPackageMetadata _packageMetadata = packageMetadata;

        // key: version, values: artifacts
        String _packageName = packageName;
        List<Pair<String, Set<PypiArtifact>>> versionArtifacts = Pipeline.of(versions)
                .map(new Function<String, Pair<String, Set<PypiArtifact>>>() {
                    @Override
                    public Pair<String, Set<PypiArtifact>> apply(String version) {
                        List<PipPackageRelease> versionReleases = _packageMetadata.getReleases().get(version);
                        Set<PypiArtifact> artifacts = Pipeline.of(versionReleases)
                                .map(new Function<PipPackageRelease, PypiArtifact>() {
                                    @Override
                                    public PypiArtifact apply(PipPackageRelease pipPackageRelease) {
                                        if (!finished.containsKey(version)) {
                                            // 说明 该版本还没有处理过
                                            PypiArtifact artifact = Pypis.gaussFileArtifact(pipPackageRelease.getFilename(), _packageName, version, pipPackageRelease.getPackagetype());
                                            artifact.setRelease(pipPackageRelease);
                                            artifact.setSupportSynchronized(true);
                                            return artifact;
                                        }
                                        return null;
                                    }
                                })
                                .clearNulls()
                                .asSet(true);

                        return new NameValuePair<Set<PypiArtifact>>(version, artifacts);
                    }
                }).filter(new Predicate<Pair<String, Set<PypiArtifact>>>() {
                    @Override
                    public boolean test(Pair<String, Set<PypiArtifact>> stringListPair) {
                        return Objs.isNotEmpty(stringListPair) && Objs.isNotEmpty(stringListPair.getValue());
                    }
                }).asList();


        if (Objs.isNotEmpty(versionArtifacts)) {
            // 下载 并 copy到 target 目录
            versionArtifacts.parallelStream()
                    .forEach(new java.util.function.Consumer<Pair<String, Set<PypiArtifact>>>() {
                        @Override
                        public void accept(Pair<String, Set<PypiArtifact>> versionArtifactPair) {
                            PackageGAV pypiPackageGAV = new PackageGAV(_packageName, _packageName, versionArtifacts.get(0).getKey());
                            if (!downloading.containsKey(pypiPackageGAV)) {
                                downloading.put(pypiPackageGAV, 1);
                                Set<PypiArtifact> artifacts = versionArtifactPair.getValue();
                                finished.putIfAbsent(versionArtifactPair.getKey(), new Holder<>(Collects.emptyArrayList()));
                                // 逐个下载 该版本的所有
                                Collects.forEach(artifacts, new Consumer<PypiArtifact>() {
                                    @Override
                                    public void accept(PypiArtifact pypiArtifact) {
                                        List<PypiArtifact> finishedArtifacts = finished.get(versionArtifactPair.getKey()).get();
                                        if (!finishedArtifacts.contains(pypiArtifact)) {
                                            finishedArtifacts.add(pypiArtifact);
                                            // 获取或者下载
                                            FileObject fileObject = null;
                                            try {
                                                fileObject = artifactManager.getArtifactFile(pypiArtifact);
                                            } catch (FileSystemException e) {
                                                logger.error(e.getMessage(), e);
                                            }
                                            // 如果已下载成功
                                            if (FileObjects.isExists(fileObject)) {
                                                // copy 到target 目录
                                                if (Objs.isNotEmpty(targetDirectory) && false) {
                                                    String targetUrl = targetDirectory;
                                                    if (!Strings.startsWith(targetDirectory, URLs.URL_PREFIX_FILE)) {
                                                        if (Strings.startsWith(targetDirectory, "/")) {
                                                            targetUrl = URLs.URL_PREFIX_FILE + targetDirectory;
                                                        } else {
                                                            targetUrl = URLs.URL_PREFIX_FILE + "/" + targetDirectory;
                                                        }
                                                        if (Strings.endsWith(targetUrl, "/")) {
                                                            targetUrl = targetUrl + FileObjects.getFileName(fileObject);
                                                        } else {
                                                            targetUrl = targetUrl + "/" + FileObjects.getFileName(fileObject);
                                                        }
                                                    }

                                                    try {
                                                        FileObject targetFile = artifactManager.getFileSystemManager().resolveFile(targetUrl);
                                                        targetFile.copyFrom(fileObject, Selectors.SELECT_SELF);
                                                    } catch (FileSystemException ex) {
                                                        logger.error(ex.getMessage(), ex);
                                                    }
                                                }

                                            } else {
                                                logger.info("Can't find the artifact: {}", JSONBuilderProvider.simplest().toJson(pypiArtifact));
                                            }
                                        }
                                    }
                                });

                                // 在 该版本的所有的artifact下载完毕后，进行依赖分析 & 下载
                                if (withDependencies) {
                                    // 先从仓库中的元数据文件中查找
                                    RequirementsArtifact requirementsArtifact = new RequirementsArtifact(versionArtifactPair.getValue().toArray(new PypiArtifact[versionArtifactPair.getValue().size()])[0].getArtifactId(), versionArtifactPair.getKey());
                                    List<String> requirements = metadataManager.get(requirementsArtifact);
                                    if (requirements == null) {
                                        // 扫描 介质包进行查找
                                        ArtifactsDependenciesFinder finder = new DefaultArtifactsDependenciesFinder();
                                        finder.setArtifactManager(artifactManager);
                                        // 从 所有的 .tar.gz, .zip, .whl, .egg 介质中找依赖
                                        requirements = finder.get(versionArtifactPair);
                                        if (Objs.isNotNull(requirements)) {
                                            metadataManager.save(requirementsArtifact, requirements);
                                        }
                                    }
                                    Collects.forEach(requirements, new Consumer<String>() {
                                        @Override
                                        public void accept(String dependency) {
                                            logger.info("prepare dependency {} for {}", dependency, _packageName);
                                            downloadPackage(dependency, withDependencies, targetDirectory, finished);
                                        }
                                    });
                                }
                                downloading.remove(pypiPackageGAV);
                            }
                        }
                    });

        }
        return true;
    }

    private List<String> selectVersions(PipPackageMetadata packageMetadata, CommonExpressionBoundary versionBoundary) {
        if (versionBoundary == null) {
            // 当没有版本范围时，
            // 优先选择 最新版本：
            String latestVersion = packageMetadata.getInfo().getVersion();
            if (Emptys.isNotEmpty(latestVersion)) {
                return Collects.newArrayList(latestVersion);
            } else {
                // 取pypi中推荐的包
                List<PipPackageRelease> recommendedReleases = packageMetadata.getUrls();
                List<String> filenames = Pipeline.of(recommendedReleases)
                        .map(new Function<PipPackageRelease, String>() {
                            @Override
                            public String apply(PipPackageRelease pipPackageRelease) {
                                return pipPackageRelease.getFilename();
                            }
                        }).asList();
                Map.Entry recommendedVersion = Collects.findFirst(packageMetadata.getReleases(), new Predicate2<String, List<PipPackageRelease>>() {
                    @Override
                    public boolean test(String version, List<PipPackageRelease> pipPackageReleases) {
                        return Collects.anyMatch(pipPackageReleases, new Predicate<PipPackageRelease>() {
                            @Override
                            public boolean test(PipPackageRelease pipPackageRelease) {
                                return filenames.contains(pipPackageRelease.getFilename());
                            }
                        });
                    }
                });
                return Collects.newArrayList((String) recommendedVersion.getKey());
            }
        } else {
            // 根据匹配规则进行匹配
            Map<String, List<PipPackageRelease>> releases = packageMetadata.getReleases();
            return Pipeline.of(releases.keySet())
                    .filter(versionBoundary)
                    .filter(new Predicate<String>() {
                        @Override
                        public boolean test(String version) {
                            List<PipPackageRelease> versionReleases = releases.get(version);
                            return Objs.isNotEmpty(versionReleases);
                        }
                    }).asList();

        }
    }

    @Override
    public ArtifactManager getArtifactManager() {
        return artifactManager;
    }

    @Override
    public Map<PackageGAV, PackageArtifact> scan(String path, Filter<PackageArtifact> filter) {
        return null;
    }
}
