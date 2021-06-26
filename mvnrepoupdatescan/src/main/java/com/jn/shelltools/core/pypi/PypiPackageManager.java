package com.jn.shelltools.core.pypi;

import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.langx.exception.IllegalParameterException;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objs;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.function.Predicate2;
import com.jn.langx.util.struct.pair.NameValuePair;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageMetadata;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageRelease;
import com.jn.shelltools.core.pypi.versionspecifier.VersionSpecifierParser;

import java.util.List;
import java.util.Map;

public class PypiPackageManager {
    private PypiService service;

    private SynchronizedArtifactManager artifactManager;

    public void setService(PypiService service) {
        this.service = service;
    }

    public PipPackageMetadata getPackageMetadata(String packageName) {
        return this.service.packageMetadata(packageName);
    }

    public void downloadPackage(String versionedPackageName, boolean whitDependencies) {
        VersionSpecifierParser parser = new VersionSpecifierParser();
        NameValuePair<CommonExpressionBoundary> parsedResult = parser.parse(versionedPackageName);
        String packageName = parsedResult.getName();
        PipPackageMetadata packageMetadata = getPackageMetadata(packageName);
        if (packageMetadata == null) {
            throw new IllegalParameterException(StringTemplates.formatWithPlaceholder("package ({}) is not exists", packageName));
        }

        CommonExpressionBoundary versionBoundary = parsedResult.getValue();

        List<String> releases = selectVersions(packageMetadata, versionBoundary);

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
            return Collects.newArrayList((String)recommendedVersion.getKey());
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
