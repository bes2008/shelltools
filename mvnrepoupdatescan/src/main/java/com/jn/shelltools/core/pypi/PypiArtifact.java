package com.jn.shelltools.core.pypi;

import com.jn.agileway.vfs.artifact.AbstractArtifact;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageRelease;

public class PypiArtifact extends AbstractArtifact {
    private PipPackageRelease release;

    public PipPackageRelease getRelease() {
        return release;
    }

    public void setRelease(PipPackageRelease release) {
        this.release = release;
    }
}
