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
import org.apache.commons.vfs2.FileObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class RequirementsManager implements DependenciesFinder<Artifact> {
    private ArtifactManager artifactManager;

    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    @Override
    public List<String> get(Artifact artifact) {
        FileObject fileObject = artifactManager.getArtifactFile(artifact);
        if (fileObject == null || !fileObject.exists()) {
            return null;
        }

        InputStream inputStream = fileObject.getContent().getInputStream();
        return readRequirements(new InputStreamResource(inputStream));
    }

    public void update(Artifact artifact, List<String> requirements){

    }

    public static List<String> readRequirements(Resource resource){
        List<String> lines = Collects.emptyArrayList();
        Resources.readUsingDelimiter(resource, "\n", Charsets.UTF_8, new Consumer<String>() {
            @Override
            public void accept(String line) {
                lines.add(line);
            }
        });
        return lines;
    }

    public static void writeRequirements(OutputStream out, List<String> requirements){
        IOs.writeLines(requirements,null, out, Charsets.UTF_8);
    }
}
