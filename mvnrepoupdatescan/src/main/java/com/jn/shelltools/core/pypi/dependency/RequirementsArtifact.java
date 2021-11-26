package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

/**
 * 本地 requirements文件，一个版本一个
 *
 * ${packageName}-${version}-requirements.txt
 */
public class RequirementsArtifact extends AbstractArtifact {
    public RequirementsArtifact(String artifactId, String version){
        setArtifactId(artifactId);
        setVersion(version);
        setClassifier("requirements");
        setExtension("txt");
    }
}
