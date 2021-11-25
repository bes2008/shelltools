package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

public class RequirementsArtifact extends AbstractArtifact {
    public RequirementsArtifact(String artifactId, String version){
        setArtifactId(artifactId);
        setVersion(version);
        setClassifier("requirements");
        setExtension("txt");
    }
}
