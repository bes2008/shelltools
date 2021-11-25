package com.jn.shelltools.core.pypi.repository;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

public class PipPackageMetadataArtifact extends AbstractArtifact {

    public PipPackageMetadataArtifact(String artifactId){
        setArtifactId(artifactId);
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getClassifier() {
        return "metadata";
    }

    @Override
    public String getExtension() {
        return "json";
    }
}
