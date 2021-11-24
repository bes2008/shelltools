package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.zip.archive.AutowiredArchiveSuiteFactory;
import com.jn.agileway.zip.archive.Expander;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.collection.Collects;
import com.jn.shelltools.core.pypi.PypiArtifact;
import com.jn.shelltools.core.pypi.Pypis;
import org.apache.commons.vfs2.FileObject;

import java.io.File;
import java.util.List;

public class SourceArtifactDependenciesFinder extends AbstractArtifactDependenciesFinder {


    @Override
    protected String expandArtifact(PypiArtifact pypiArtifact, FileObject tmpFileObject) {
        /*
        Resource resource = Resources.loadFileResource("file:" + sour_targz);

        Expander expander = AutowiredArchiveSuiteFactory.getInstance().get("tar.gz", resource.getInputStream());
        expander.setOverwriteExistsFiles(true);
        expander.expandTo(new File("e:/tmp/t002"));
        expander.close();
        */
        return null;
    }

    @Override
    public List<String> supportedExtensions() {
        return Collects.asList(Pypis.getFileExtensions("sdist"));
    }
}
