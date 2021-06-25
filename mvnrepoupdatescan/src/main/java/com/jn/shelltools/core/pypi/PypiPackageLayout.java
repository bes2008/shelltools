package com.jn.shelltools.core.pypi;

import com.jn.agileway.vfs.artifact.Artifact;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepository;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryLayout;

public class PypiPackageLayout implements ArtifactRepositoryLayout {
    @Override
    public String getPath(ArtifactRepository artifactRepository, Artifact artifact) {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getName() {
        return "pypi";
    }
}
