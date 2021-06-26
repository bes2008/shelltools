package com.jn.shelltools.core.pypi;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

public class PypiArtifact extends AbstractArtifact {
    private String downloadUrl;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
