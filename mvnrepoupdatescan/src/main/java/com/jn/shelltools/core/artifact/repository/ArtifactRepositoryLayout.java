package com.jn.shelltools.core.artifact.repository;

import com.jn.shelltools.core.artifact.Artifact;

public interface ArtifactRepositoryLayout {
    String getPath(Artifact artifact);
}
