package com.jn.shelltools.core.pypi;

import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.langx.exception.IllegalParameterException;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;
import com.jn.langx.util.Throwables;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.*;
import com.jn.langx.util.struct.pair.NameValuePair;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageMetadata;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageRelease;
import com.jn.shelltools.core.pypi.versionspecifier.VersionSpecifierParser;
import org.apache.commons.vfs2.FileObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PypiPackageManager {
    private static final Logger logger = LoggerFactory.getLogger(PypiPackageManager.class);
    private PypiService service;

    private SynchronizedArtifactManager artifactManager;

    public void setService(PypiService service) {
        this.service = service;
    }

    public PipPackageMetadata getPackageMetadata(String packageName) {
        return this.service.packageMetadata(packageName);
    }

    public void setArtifactManager(SynchronizedArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    public void downloadPackage(String versionedPackageName, boolean whitDependencies) {
        VersionSpecifierParser parser = new VersionSpecifierParser();
        NameValuePair<CommonExpressionBoundary> parsedResult = parser.parse(versionedPackageName);
        String packageName = parsedResult.getName();
        PipPackageMetadata packageMetadata = null;
        try {
            packageMetadata = getPackageMetadata(packageName);
        } catch (Throwable ex) {
            throw Throwables.wrapAsRuntimeException(ex);
        }
        if (packageMetadata == null) {
            throw new IllegalParameterException(StringTemplates.formatWithPlaceholder("package ({}) is not exists", packageName));
        }

        CommonExpressionBoundary versionBoundary = parsedResult.getValue();
        List<String> versions = selectVersions(packageMetadata, versionBoundary);
        logger.info("selected versions: {}", Strings.join(",", versions));

        PipPackageMetadata _packageMetadata = packageMetadata;
        List<PypiArtifact> artifacts = Pipeline.of(versions)
                .map(new Function<String, List<PypiArtifact>>() {
                    @Override
                    public List<PypiArtifact> apply(String version) {
                        List<PipPackageRelease> versionReleases = _packageMetadata.getReleases().get(version);
                        return Pipeline.of(versionReleases)
                                .map(new Function<PipPackageRelease, PypiArtifact>() {
                                    @Override
                                    public PypiArtifact apply(PipPackageRelease pipPackageRelease) {
                                        PypiArtifact artifact = Pypis.gaussFileArtifact(pipPackageRelease.getFilename(), packageName, version, pipPackageRelease.getPackagetype());
                                        artifact.setRelease(pipPackageRelease);
                                        return artifact;
                                    }
                                })
                                .asList();

                    }
                })
                .flatMap(Functions.<PypiArtifact>noopFunction())
                .asList();

        // 开始下载，目前是串行方式，后面改为并行方式
        Pipeline.of(artifacts)
                .forEach(new Consumer<PypiArtifact>() {
                    @Override
                    public void accept(PypiArtifact pypiArtifact) {
                        FileObject fileObject = artifactManager.getArtifactFile(pypiArtifact);
                    }
                });
    }

    private List<String> selectVersions(PipPackageMetadata packageMetadata, CommonExpressionBoundary versionBoundary) {
        if (versionBoundary == null) {
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
        // 取版本匹配的
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
