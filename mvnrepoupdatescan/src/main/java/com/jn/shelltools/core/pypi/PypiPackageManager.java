package com.jn.shelltools.core.pypi;

import com.jn.agileway.vfs.FileObjects;
import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.easyjson.core.JSONBuilderProvider;
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
import com.jn.shelltools.core.pypi.dependency.ArtifactsDependenciesFinder;
import com.jn.shelltools.core.pypi.dependency.DefaultArtifactsDependenciesFinder;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageMetadata;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageRelease;
import com.jn.shelltools.core.pypi.versionspecifier.VersionSpecifierParser;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.Selectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PypiPackageManager {
    private static final Logger logger = LoggerFactory.getLogger(PypiPackageManager.class);

    private SynchronizedArtifactManager artifactManager;
    private PypiPackageMetadataManager metadataManager;

    public void setMetadataManager(PypiPackageMetadataManager metadataManager) {
        this.metadataManager = metadataManager;
    }


    public void setArtifactManager(SynchronizedArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    public void downloadPackage(@NotEmpty String versionedPackageName, final boolean withDependencies, @Nullable String targetDirectory) {
        VersionSpecifierParser parser = new VersionSpecifierParser();
        NameValuePair<CommonExpressionBoundary> parsedResult = parser.parse(versionedPackageName);
        String packageName = parsedResult.getName();
        PipPackageMetadata packageMetadata = null;
        try {
            packageMetadata = metadataManager.getOfficialMetadata(packageName);
        } catch (Throwable ex) {
            throw Throwables.wrapAsRuntimeException(ex);
        }
        if (packageMetadata == null) {
            throw new IllegalParameterException(StringTemplates.formatWithPlaceholder("package ({}) is not exists", packageName));
        }

        CommonExpressionBoundary versionBoundary = parsedResult.getValue();
        List<String> versions = selectVersions(packageMetadata, versionBoundary);
        logger.info("selected {} versions: {}", packageName, Strings.join(",", versions));

        PipPackageMetadata _packageMetadata = packageMetadata;
        // 将 pypi 仓库官方提供的 metadata 写到本地仓库中，文件名格式: <dist>_<version>_metadata.json

        // key: version, values: artifacts
        List<Pair<String, List<PypiArtifact>>> versionArtifacts = Pipeline.of(versions)
                .map(new Function<String, Pair<String, List<PypiArtifact>>>() {
                    @Override
                    public Pair<String, List<PypiArtifact>> apply(String version) {
                        List<PipPackageRelease> versionReleases = _packageMetadata.getReleases().get(version);
                        List<PypiArtifact> artifacts = Pipeline.of(versionReleases)
                                .map(new Function<PipPackageRelease, PypiArtifact>() {
                                    @Override
                                    public PypiArtifact apply(PipPackageRelease pipPackageRelease) {
                                        PypiArtifact artifact = Pypis.gaussFileArtifact(pipPackageRelease.getFilename(), packageName, version, pipPackageRelease.getPackagetype());
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
                                FileObject fileObject = artifactManager.getArtifactFile(pypiArtifact);
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

                            ArtifactsDependenciesFinder finder = new DefaultArtifactsDependenciesFinder();
                            finder.setArtifactManager(artifactManager);
                            List<String> dependencies = finder.get(versionArtifactPair);
                            Collects.forEach(dependencies, new Consumer<String>() {
                                @Override
                                public void accept(String dependency) {
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
}
