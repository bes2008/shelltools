package com.jn.shelltools.core.pypi.repository;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

public class PipPackageMetadataArtifact extends AbstractArtifact {

    public PipPackageMetadataArtifact(String artifactId){
        setArtifactId(artifactId);
        setClassifier("metadata");
        setExtension("json");
    }

    @Override
    public String getVersion() {
        return null;
    }
}
