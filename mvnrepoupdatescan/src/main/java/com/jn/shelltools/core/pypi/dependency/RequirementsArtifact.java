package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

public class RequirementsArtifact extends AbstractArtifact {
    public RequirementsArtifact(String artifactId, String version){
        setArtifactId(artifactId);
        setVersion(version);
    }

    @Override
    public String getClassifier() {
        return "requirements";
    }

    @Override
    public String getExtension() {
        return "txt";
    }
}
