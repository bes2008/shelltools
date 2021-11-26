package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.vfs.artifact.Artifact;
import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.langx.io.resource.InputStreamResource;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.logging.Loggers;
import org.apache.commons.vfs2.FileObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class RequirementsManager implements DependenciesFinder<Artifact> {
    private static final Logger logger = Loggers.getLogger(RequirementsManager.class);
    protected ArtifactManager artifactManager;

    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    @Override
    public List<String> get(Artifact artifact) {
        InputStream inputStream = null;
        try {
            FileObject fileObject = artifactManager.getArtifactFile(artifact);
            if (!fileObject.exists()) {
                return null;
            }

            inputStream = fileObject.getContent().getInputStream();
            return readRequirements(new InputStreamResource(inputStream));
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            IOs.close(inputStream);
        }
        return null;
    }

    public void save(Artifact artifact, List<String> requirements) {
        OutputStream out = null;
        try {
            FileObject fileObject = artifactManager.getArtifactFile(artifact);
            if (!fileObject.exists()) {
                fileObject.getParent().createFolder();
                fileObject.createFile();
            }
            out = fileObject.getContent().getOutputStream(false);
            writeRequirements(out, requirements);
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            IOs.close(out);
        }
    }

    public static List<String> readRequirements(Resource resource) {
        List<String> lines = Collects.emptyArrayList();
        Resources.readUsingDelimiter(resource, "\n", Charsets.UTF_8, new Consumer<String>() {
            @Override
            public void accept(String line) {
                lines.add(line);
            }
        });
        return lines;
    }

    public static void writeRequirements(OutputStream out, List<String> requirements) throws IOException {
        IOs.writeLines(requirements, null, out, Charsets.UTF_8);
    }
}
