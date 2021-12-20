package com.jn.shelltools.supports.pypi;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.agileway.vfs.filter.*;
import com.jn.agileway.vfs.utils.FileObjects;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.Filter;
import com.jn.langx.annotation.NotEmpty;
import com.jn.langx.annotation.Nullable;
import com.jn.langx.el.boundary.CommonExpressionBoundary;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Emptys;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;
import com.jn.langx.util.Throwables;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.ConcurrentHashSet;
import com.jn.langx.util.collection.DistinctLinkedBlockingQueue;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.*;
import com.jn.langx.util.function.predicate.filter.FilterToPredicateAdapter;
import com.jn.langx.util.net.URLs;
import com.jn.langx.util.os.Platform;
import com.jn.langx.util.struct.Holder;
import com.jn.langx.util.struct.Pair;
import com.jn.langx.util.struct.pair.NameValuePair;
import com.jn.shelltools.core.LocalPackageScanner;
import com.jn.shelltools.core.LocalPackageArtifact;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

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
    private ConcurrentHashSet<String> downloadingPackages = new ConcurrentHashSet<>();
    private ConcurrentHashSet<PackageGAV> downloadingGavs = new ConcurrentHashSet<PackageGAV>();
    private ThreadPoolExecutor executor;

    public PypiPackageManager() {
        executor = new ThreadPoolExecutor(Platform.cpuCore() * 2, Platform.cpuCore() * 2, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public void setMetadataManager(PypiPackageMetadataManager metadataManager) {
        this.metadataManager = metadataManager;
    }


    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    public void downloadPackages(DistinctLinkedBlockingQueue<String> packages, final boolean withDependencies, Predicate<PypiArtifact> artifactPredicate) {
        Map<String, Holder<List<PypiArtifact>>> finished = new ConcurrentHashMap<>();
        //  downloadPackage(versionedPackageName, withDependencies, artifactPredicate, packages, finished);
        String packageName = null;
        // main thread
        while (true) {
            try {
                packageName = packages.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                logger.error("downloading is interrupted");
            }
            if (Strings.isBlank(packageName)) {
                if (executor.getActiveCount() == 0) {
                    logger.info("finished");
                    return;
                }
            } else {
                final String _packageName = packageName;
                packageName = null;
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            downloadPackage(_packageName, withDependencies, artifactPredicate, packages, finished);
                        } catch (Throwable ex) {
                            logger.error(ex.getMessage(), ex);
                        }
                    }
                });

            }
        }
    }


    public void downloadPackage(@NotEmpty String versionedPackageName, final boolean withDependencies, Predicate<PypiArtifact> artifactPredicate) {
        DistinctLinkedBlockingQueue<String> dependencyPackageNames = new DistinctLinkedBlockingQueue<String>();
        dependencyPackageNames.add(versionedPackageName);
        downloadPackages(dependencyPackageNames, withDependencies, artifactPredicate);
    }

    private void abnormalFinished(Map<String, Holder<List<PypiArtifact>>> finished, String packageName) {
        downloadingPackages.remove(packageName);
        finished.put(packageName, new Holder<>());
    }

    /**
     * 下载至本地仓库,一次只下载一个包
     *
     * @param versionedPackageName
     * @param withDependencies
     * @param artifactPredicate
     */
    private void downloadPackage(@NotEmpty String versionedPackageName, final boolean withDependencies, @Nullable final Predicate<PypiArtifact> artifactPredicate, DistinctLinkedBlockingQueue<String> pendingPackageNames, Map<String, Holder<List<PypiArtifact>>> finished) {
        if (Strings.isBlank(versionedPackageName)) {
            return;
        }
        if (finished.containsKey(versionedPackageName)) {
            return;
        }
        if (downloadingPackages.contains(versionedPackageName)) {
            return;
        }
        downloadingPackages.add(versionedPackageName);
        final Predicate<PypiArtifact> _artifactPredicate = artifactPredicate == null ? Functions.truePredicate() : artifactPredicate;
        String packageName = null;
        @Nullable
        CommonExpressionBoundary versionBoundary = null;
        if (Strings.isBlank(versionedPackageName) || Strings.isBlank(VersionSpecifiers.extractPackageName(versionedPackageName))) {
            logger.error("invalid package name {}", versionedPackageName);
            if (Strings.isNotBlank(versionedPackageName)) {
                abnormalFinished(finished, versionedPackageName);
            }
            return;
        } else {
            versionedPackageName = VersionSpecifiers.extractPackageName(versionedPackageName);
        }
        if (Strings.isBlank(versionedPackageName)) {
            abnormalFinished(finished, versionedPackageName);
            logger.error("invalid package name {}", versionedPackageName);
            return;
        }
        if (VersionSpecifiers.versionAbsent(versionedPackageName)) {
            packageName = versionedPackageName;
        } else {
            if (VersionSpecifiers.versionedPackageName(versionedPackageName)) {
                VersionSpecifierParser parser = new VersionSpecifierParser();
                NameValuePair<CommonExpressionBoundary> parsedResult = parser.parse(versionedPackageName);
                if (parsedResult != null) {
                    packageName = parsedResult.getName();
                    versionBoundary = parsedResult.getValue();
                }
            } else {
                abnormalFinished(finished, versionedPackageName);
                logger.error("invalid package name {}", versionedPackageName);
                return;
            }
        }
        PipPackageMetadata packageMetadata = null;
        if (Strings.isBlank(packageName) || Strings.containsAny(packageName, "%,()")) {
            abnormalFinished(finished, versionedPackageName);
            logger.error("invalid package name {}", versionedPackageName);
            return;
        }
        if (!finished.containsKey(packageName)) {
            try {
                packageMetadata = metadataManager.getOfficialMetadata(packageName);
            } catch (Throwable ex) {
                throw Throwables.wrapAsRuntimeException(ex);
            }
        } else {
            return;
        }
        if (packageMetadata == null) {
            logger.error(StringTemplates.formatWithPlaceholder("package ({}) is not exists", packageName));
            abnormalFinished(finished, versionedPackageName);
            abnormalFinished(finished, packageName);
            return;
        }


        List<String> versions = selectVersions(packageMetadata, versionBoundary);
        logger.debug("selected {} versions: {}", packageName, Strings.join(",", versions));

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
                                        String _versionedPackageName = _packageName + "=" + version;
                                        if (!finished.containsKey(_versionedPackageName)) {
                                            // 说明 该版本还没有处理过
                                            PypiArtifact artifact = Pypis.gaussFileArtifact(pipPackageRelease.getFilename(), _packageName, version, pipPackageRelease.getPackagetype());
                                            if (artifact != null) {
                                                artifact.setRelease(pipPackageRelease);
                                                artifact.setSupportSynchronized(true);
                                                return artifact;
                                            }
                                        }
                                        return null;
                                    }
                                })
                                .clearNulls()
                                .filter(_artifactPredicate)
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
                            String packageVersion = versionArtifactPair.getKey();
                            PackageGAV pypiPackageGAV = new PackageGAV(_packageName, _packageName, packageVersion);
                            if (!downloadingGavs.contains(pypiPackageGAV)) {
                                downloadingGavs.add(pypiPackageGAV);
                                Set<PypiArtifact> artifacts = versionArtifactPair.getValue();
                                finished.putIfAbsent(pypiPackageGAV.toString(), new Holder<>(new CopyOnWriteArrayList<>()));
                                // 逐个下载 该版本的所有
                                Collects.forEach(artifacts, new Consumer<PypiArtifact>() {
                                    @Override
                                    public void accept(PypiArtifact pypiArtifact) {
                                        List<PypiArtifact> finishedArtifacts = finished.get(pypiPackageGAV.toString()).get();
                                        if (!finishedArtifacts.contains(pypiArtifact)) {
                                            logger.debug("sync: {}", pypiArtifact);
                                            finishedArtifacts.add(pypiArtifact);
                                            // 获取或者下载
                                            FileObject fileObject = null;
                                            try {
                                                fileObject = artifactManager.getArtifactFile(pypiArtifact);
                                            } catch (FileSystemException e) {
                                                logger.error(e.getMessage(), e);
                                            }
                                            // copy 到target 目录
                                            String targetDirectory = null;
                                            if (Objs.isNotEmpty(targetDirectory) && false) {
                                                if (FileObjects.isExists(fileObject)) {
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

                                                } else {
                                                    logger.info("Can't find the artifact: {}", JSONBuilderProvider.simplest().toJson(pypiArtifact));
                                                }
                                            }
                                        }
                                    }
                                });

                                downloadingGavs.remove(pypiPackageGAV);

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
                                            dependency = Strings.join("", Strings.split(dependency, " "));
                                            pendingPackageNames.add(dependency);
                                        }
                                    });
                                }
                            }
                        }
                    });
        } else {
            abnormalFinished(finished, _packageName);
            abnormalFinished(finished, versionedPackageName);
        }
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
    public Map<PackageGAV, LocalPackageArtifact> scan(String relativePath, Filter<LocalPackageArtifact> filter) {
        FileObject fileObject = artifactManager.getFile(relativePath);
        Map<PackageGAV, LocalPackageArtifact> result = new HashMap<PackageGAV, LocalPackageArtifact>();
        try {
            if (fileObject.isFolder()) {
                // 找到目录下有 -metadata.json
                String directoryName = fileObject.getName().getBaseName();
                /**
                 * @see com.jn.agileway.vfs.artifact.AbstractArtifact.PipPackageMetadataArtifact
                 */
                FileObject metadataFile = fileObject.getChild(directoryName + "-metadata.json");
                if (FileObjects.isExists(metadataFile)) {
                    List<LocalPackageArtifact> versions = scanPackageVersions(fileObject, filter);
                    Collects.forEach(versions, new Consumer<LocalPackageArtifact>() {
                        @Override
                        public void accept(LocalPackageArtifact localPackageArtifact) {
                            PackageGAV packageGAV = new PackageGAV();
                            packageGAV.setGroupId(localPackageArtifact.getGroupId());
                            packageGAV.setArtifactId(localPackageArtifact.getArtifactId());
                            packageGAV.setVersion(localPackageArtifact.getVersion());

                            result.put(packageGAV, localPackageArtifact);
                        }
                    });
                } else {
                    // 找到 该目录下所有的 符合条件的子目录
                    List<FileObject> children = FileObjects.findChildren(fileObject, new IsDirectoryFilter());
                    children = Pipeline.of(children).filter(new Predicate<FileObject>() {
                        @Override
                        public boolean test(FileObject child) {
                            try {
                                FileObject metadataFile = child.getChild(child.getName().getBaseName() + "-metadata.json");
                                if (FileObjects.isExists(metadataFile)) {
                                    return true;
                                }
                            } catch (Throwable ex) {
                                logger.error(ex.getMessage());
                            }
                            return false;
                        }
                    }).asList();

                    if (!children.isEmpty()) {
                        Collects.forEach(children, new Consumer<FileObject>() {
                            @Override
                            public void accept(FileObject fileObject) {
                                List<LocalPackageArtifact> versions = scanPackageVersions(fileObject, filter);
                                Collects.forEach(versions, new Consumer<LocalPackageArtifact>() {
                                    @Override
                                    public void accept(LocalPackageArtifact localPackageArtifact) {
                                        PackageGAV packageGAV = new PackageGAV();
                                        packageGAV.setGroupId(localPackageArtifact.getGroupId());
                                        packageGAV.setArtifactId(localPackageArtifact.getArtifactId());
                                        packageGAV.setVersion(localPackageArtifact.getVersion());

                                        result.put(packageGAV, localPackageArtifact);
                                    }
                                });
                            }
                        });
                    }
                }
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    private List<LocalPackageArtifact> scanPackageVersions(FileObject currentPackageRootDir, Filter<LocalPackageArtifact> filter) {
        final String packageName = currentPackageRootDir.getName().getBaseName();
        PipPackageMetadata packageMetadata = metadataManager.getOfficialMetadata(packageName);
        if (packageMetadata == null) {
            return Collects.immutableList();
        }
        // 找到所有的 确认存在的 版本目录：
        List<FileObject> children = FileObjects.findChildren(currentPackageRootDir, new AllMatchFileFilter(new IsDirectoryFilter(), new FileObjectFilter() {
            @Override
            public boolean test(FileObject child) {
                String childDirectoryName = child.getName().getBaseName();
                return packageMetadata.getReleases().containsKey(childDirectoryName);
            }
        }));
        return Pipeline.of(children)
                .filter(new Predicate<FileObject>() {
                    @Override
                    public boolean test(FileObject versionDir) {
                        List<FileObject> mediaFiles = FileObjects.findChildren(versionDir, new AllMatchFileFilter(new IsFileFilter(), new FilenamePrefixFileFilter(false, packageName), new FilenameSuffixFileFilter(true, Pypis.getAllFileExtensions().toArray(new String[0]))));
                        return Objs.isNotEmpty(mediaFiles);
                    }
                })
                .map(new Function<FileObject, LocalPackageArtifact>() {
                    @Override
                    public LocalPackageArtifact apply(FileObject versionDir) {
                        String version = versionDir.getName().getBaseName();
                        LocalPackageArtifact artifact = new LocalPackageArtifact();
                        artifact.setGroupId(packageName);
                        artifact.setArtifactId(packageName);
                        artifact.setVersion(version);
                        artifact.setLocalPath(versionDir.getName().getPath());
                        try {
                            artifact.setLastModified(versionDir.getContent().getLastModifiedTime());
                        } catch (FileSystemException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return artifact;
                    }
                }).filter(new FilterToPredicateAdapter<>(filter)).asList();

    }

    public boolean packageHasManaged(String packageName) {
        return metadataManager.isPackageInRepository(packageName);
    }


    public Map<String, String> getLicenses(List<String> packageNames) {
        return getLicenses(packageNames, false);
    }

    public Map<String, String> getLicenses(List<String> packageNames, boolean allLocalPackage) {
        Map<String, String> map = Collects.emptyHashMap();
        if (Objs.isEmpty(packageNames)) {
            if (allLocalPackage) {
                Map<PackageGAV, LocalPackageArtifact> versionedPackageMap = scan(null, null);
                Set<String> allPackageNames = Pipeline.of(versionedPackageMap.keySet())
                        .map(new Function<PackageGAV, String>() {
                            @Override
                            public String apply(PackageGAV packageGAV) {
                                return packageGAV.getArtifactId();
                            }
                        }).asSet(false);

                packageNames = Collects.asList(allPackageNames);
            }
        }
        Pipeline.of(metadataManager.findMetadatas(packageNames))
                .forEach(new Consumer<PipPackageMetadata>() {
                    @Override
                    public void accept(PipPackageMetadata pipPackageMetadata) {
                        map.put(pipPackageMetadata.getInfo().getName(), pipPackageMetadata.getInfo().getLicense());
                    }
                });

        return map;
    }
}
