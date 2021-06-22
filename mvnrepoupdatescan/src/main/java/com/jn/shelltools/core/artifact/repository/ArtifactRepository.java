package com.jn.shelltools.core.artifact.repository;

import com.jn.shelltools.core.artifact.Artifact;

public interface ArtifactRepository {
    String getId();
    void setId(String id);

    String getUrl();
    void setUrl(String url);

    String getBasedir();
    String getProtocol();

    ArtifactRepositoryLayout getLayout();

    String getPath(Artifact artifact);
    boolean isEnabled();
}
