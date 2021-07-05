package com.jn.shelltools.core.pypi.dependency;

import com.jn.langx.util.collection.Collects;
import com.jn.shelltools.core.pypi.PypiArtifact;
import com.jn.shelltools.core.pypi.Pypis;

import java.util.List;

public class SourceArtifactDependenciesFinder implements ArtifactDependenciesFinder{
    @Override
    public List<String> get(PypiArtifact artifact) {

        return null;
    }

    @Override
    public List<String> supportedExtensions() {
        return Collects.asList(Pypis.getFileExtensions("sdist"));
    }
}
