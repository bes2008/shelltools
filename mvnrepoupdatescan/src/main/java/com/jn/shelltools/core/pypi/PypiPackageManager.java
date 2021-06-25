package com.jn.shelltools.core.pypi;

import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.struct.pair.NameValuePair;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageMetadata;
import com.jn.shelltools.core.pypi.versionspecifier.VersionSpecifierParser;

public class PypiPackageManager {
    private PypiService service;

    private SynchronizedArtifactManager artifactManager;

    public void setService(PypiService service) {
        this.service = service;
    }

    public PipPackageMetadata getPackageMetadata(String packageName) {
        return this.service.packageSummary(packageName);
    }

    public void downloadPackage(String versionedPackageName) {
        VersionSpecifierParser parser = new VersionSpecifierParser();
        NameValuePair<CommonExpressionBoundary> parsedResult = parser.parse(versionedPackageName);
        String packageName = parsedResult.getName();
        CommonExpressionBoundary versionBoundary = parsedResult.getValue();
        PipPackageMetadata packageMetadata = getPackageMetadata(packageName);


    }

}
