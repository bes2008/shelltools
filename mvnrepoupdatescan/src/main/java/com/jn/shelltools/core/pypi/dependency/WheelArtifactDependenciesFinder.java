package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.zip.archive.AutowiredArchiveSuiteFactory;
import com.jn.agileway.zip.archive.Expander;
import com.jn.langx.io.resource.FileResource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.io.file.Filenames;
import com.jn.langx.util.io.file.Files;
import com.jn.shelltools.core.pypi.PypiArtifact;
import com.jn.shelltools.core.pypi.Pypis;
import org.apache.commons.vfs2.FileObject;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;

public class WheelArtifactDependenciesFinder extends AbstractArtifactDependenciesFinder {

    @Override
    protected boolean isArchive(PypiArtifact pypiArtifact) {
        return "whl".equalsIgnoreCase(pypiArtifact.getExtension());
    }

    @Override
    protected String expandArtifact(PypiArtifact pypiArtifact, FileObject tmpFileObject) {
        try {
            File localTempFile = Files.toFile(new URL(tmpFileObject.getName().getURI()));
            FileResource resource = Resources.loadFileResource(localTempFile);

            Expander expander = AutowiredArchiveSuiteFactory.getInstance().get("zip", resource.getInputStream());
            expander.setOverwriteExistsFiles(true);

            String dirname = Filenames.extractFilename(localTempFile.getAbsolutePath(), false);
            File tmpExpandDir = new File(localTempFile.getParentFile(), dirname);
            expander.expandTo(tmpExpandDir);
            expander.close();
            return tmpExpandDir.getAbsolutePath();
        } catch (Throwable ex) {
            LoggerFactory.getLogger(WheelArtifactDependenciesFinder.class).error(ex.getMessage(), ex);
        }
        return null;
    }


    @Override
    public List<String> supportedExtensions() {
        return Collects.asList(Pypis.getFileExtensions("bdist_wheel"));
    }

    @Override
    protected List<String> parseDependencies(PypiArtifact pypiArtifact, String tmpExpandDir) {

        return null;
    }
}
