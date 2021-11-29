package com.jn.shelltools.supports.pypi;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.agileway.vfs.utils.FileObjects;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.Filter;
import com.jn.langx.annotation.NotEmpty;
import com.jn.langx.annotation.Nullable;
import com.jn.langx.exception.IllegalParameterException;
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

    public void setMetadataManager(PypiPackageMetadataManager metadataManager) {
        this.metadataManager = metadataManager;
    }


    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    /**
     * 下载至本地仓库
     *
     * @param versionedPackageName
     * @param withDependencies
     * @param targetDirectory
     */
    public void downloadPackage(@NotEmpty String versionedPackageName, final boolean withDependencies, @Nullable String targetDirectory) {
        String packageName = null;
        CommonExpressionBoundary versionBoundary = null;
        if(VersionSpecifiers.versionAbsent(versionedPackageName)){
            packageName = versionedPackageName;
        }else {
            VersionSpecifierParser parser = new VersionSpecifierParser();
            NameValuePair<CommonExpressionBoundary> parsedResult = parser.parse(versionedPackageName);
            packageName = parsedResult.getName();
            versionBoundary = parsedResult.getValue();
        }
        PipPackageMetadata packageMetadata = null;
        try {
            packageMetadata = metadataManager.getOfficialMetadata(packageName);
        } catch (Throwable ex) {
            throw Throwables.wrapAsRuntimeException(ex);
        }
        if (packageMetadata == null) {
            logger.error(StringTemplates.formatWithPlaceholder("package ({}) is not exists", packageName));
            return;
        }


        List<String> versions = selectVersions(packageMetadata, versionBoundary);
        logger.info("selected {} versions: {}", packageName, Strings.join(",", versions));

        PipPackageMetadata _packageMetadata = packageMetadata;
        // 将 pypi 仓库官方提供的 metadata 写到本地仓库中，文件名格式: <dist>_<version>_metadata.json

        // key: version, values: artifacts
        String _packageName = packageName;
        List<Pair<String, List<PypiArtifact>>> versionArtifacts = Pipeline.of(versions)
                .map(new Function<String, Pair<String, List<PypiArtifact>>>() {
                    @Override
                    public Pair<String, List<PypiArtifact>> apply(String version) {
                        List<PipPackageRelease> versionReleases = _packageMetadata.getReleases().get(version);
                        List<PypiArtifact> artifacts = Pipeline.of(versionReleases)
                                .map(new Function<PipPackageRelease, PypiArtifact>() {
                                    @Override
                                    public PypiArtifact apply(PipPackageRelease pipPackageRelease) {
                                        PypiArtifact artifact = Pypis.gaussFileArtifact(pipPackageRelease.getFilename(), _packageName, version, pipPackageRelease.getPackagetype());
                                        artifact.setRelease(pipPackageRelease);
                                        artifact.setSupportSynchronized(true);
                                        return artifact;
                                    }
                                })
                                .asList();

                        return new NameValuePair<List<PypiArtifact>>(version, artifacts);
                    }
                }).asList();

        // 构建该 package的 自定义metadata

        // 下载 并 copy到 target 目录
        versionArtifacts.parallelStream()
                .forEach(new java.util.function.Consumer<Pair<String, List<PypiArtifact>>>() {
                    @Override
                    public void accept(Pair<String, List<PypiArtifact>> versionArtifactPair) {
                        List<PypiArtifact> artifacts = versionArtifactPair.getValue();

                        // 逐个下载 该版本的所有
                        Collects.forEach(artifacts, new Consumer<PypiArtifact>() {
                            @Override
                            public void accept(PypiArtifact pypiArtifact) {
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
                                    if (Objs.isNotEmpty(targetDirectory)) {
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
                        });

                        // 在 该版本的所有的artifact下载完毕后，进行依赖分析 & 下载
                        if (withDependencies) {
                            // 先从仓库中的元数据文件中查找
                            RequirementsArtifact requirementsArtifact = new RequirementsArtifact(versionArtifactPair.getValue().get(0).getArtifactId(), versionArtifactPair.getKey());
                            List<String> requirements = metadataManager.get(requirementsArtifact);
                            if (requirements == null) {
                                // 扫描 介质包进行查找
                                ArtifactsDependenciesFinder finder = new DefaultArtifactsDependenciesFinder();
                                finder.setArtifactManager(artifactManager);
                                // 从 所有的 .tar.gz, .zip, .whl, .egg 介质中找依赖
                                requirements = finder.get(versionArtifactPair);
                            }
                            if (Objs.isNotEmpty(requirements)) {
                                metadataManager.save(requirementsArtifact, requirements);
                            }
                            Collects.forEach(requirements, new Consumer<String>() {
                                @Override
                                public void accept(String dependency) {
                                    logger.info("prepare dependency {} for {}", dependency, _packageName);
                                    downloadPackage(dependency, withDependencies, targetDirectory);
                                }
                            });
                        }
                    }
                });
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
