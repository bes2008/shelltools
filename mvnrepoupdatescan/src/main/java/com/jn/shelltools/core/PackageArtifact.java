package com.jn.shelltools.core;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

/**
 * 包级别的制品，具体到版本，不会体现 classifier, extension
 * <p>
 * 就是一个 软件包的一个版本的情况。
 */
public class PackageArtifact extends AbstractArtifact {
    private long lastModified;

    @Override
    public String getClassifier() {
        return null;
    }

    @Override
    public String getExtension() {
        return null;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
