package com.jn.shelltools.core.pypi.dependency;

import com.jn.shelltools.core.pypi.PypiArtifact;

import java.util.List;

public class DependenciesMetadataManager implements DependenciesFinder<PypiArtifact> {
    @Override
    public List<String> get(PypiArtifact artifact) {
        return null;
    }
}
