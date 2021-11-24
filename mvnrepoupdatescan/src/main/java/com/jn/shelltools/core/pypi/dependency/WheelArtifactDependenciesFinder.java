package com.jn.shelltools.core.pypi.dependency;

import com.jn.langx.util.collection.Collects;
import com.jn.shelltools.core.pypi.PypiArtifact;
import com.jn.shelltools.core.pypi.Pypis;
import org.apache.commons.vfs2.FileObject;

import java.util.List;

public class WheelArtifactDependenciesFinder extends AbstractArtifactDependenciesFinder{
    @Override
    protected String expandArtifact(PypiArtifact pypiArtifact, FileObject tmpFileObject) {
        return null;
    }

    @Override
    public List<String> supportedExtensions() {
        return Collects.asList(Pypis.getFileExtensions("bdist_wheel"));
    }
}
