package com.jn.shelltools.core.pypi;

import com.jn.shelltools.core.artifact.SynchronizedArtifactManager;
import com.jn.shelltools.core.pypi.model.packagemetadata.PipPackageMetadata;
import org.springframework.beans.factory.annotation.Autowired;

public class PipPackageManager {
    private PipService service;

    private SynchronizedArtifactManager artifactManager;

    @Autowired
    public void setService(PipService service) {
        this.service = service;
    }

    public PipPackageMetadata getPackageSummary(String packageName) {
        return this.service.packageSummary(packageName);
    }

    public void downloadPackage(String versionedPackageName){

    }

}
