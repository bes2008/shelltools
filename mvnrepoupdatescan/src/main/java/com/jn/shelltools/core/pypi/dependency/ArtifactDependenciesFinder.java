package com.jn.shelltools.core.pypi.dependency;

import com.jn.shelltools.core.pypi.PypiArtifact;

import java.util.List;

public interface ArtifactDependenciesFinder extends DependenciesFinder<PypiArtifact> {
    @Override
    List<String> get(PypiArtifact artifact);
}
