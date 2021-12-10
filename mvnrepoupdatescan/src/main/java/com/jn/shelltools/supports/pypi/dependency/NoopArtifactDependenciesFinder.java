package com.jn.shelltools.supports.pypi.dependency;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.langx.util.collection.Collects;
import com.jn.shelltools.supports.pypi.PypiArtifact;
import com.jn.shelltools.supports.pypi.Pypis;

import java.util.List;

public class NoopArtifactDependenciesFinder implements ArtifactDependenciesFinder{
    private List<String> extensions = Collects.newArrayList(
            Pypis.ARCHIVE_EXTENSION_EXE,
            Pypis.ARCHIVE_EXTENSION_MSI,
            Pypis.ARCHIVE_EXTENSION_RPM
    );
    @Override
    public List<String> get(PypiArtifact artifact) {
        return null;
    }

    @Override
    public List<String> supportedExtensions() {
        return extensions;
    }

    @Override
    public void setArtifactManager(ArtifactManager artifactManager) {

    }

    @Override
    public ArtifactManager getArtifactManager() {
        return null;
    }
}
