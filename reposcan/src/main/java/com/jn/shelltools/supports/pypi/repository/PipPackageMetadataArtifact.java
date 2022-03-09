package com.jn.shelltools.supports.pypi.repository;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

/**
 * 本地元数据文件
 * ${packageName}-${version}-metadata.json
 */
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
