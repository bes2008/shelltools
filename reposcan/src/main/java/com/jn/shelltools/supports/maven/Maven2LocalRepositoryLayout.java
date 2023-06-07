package com.jn.shelltools.supports.maven;

import com.jn.agileway.vfs.artifact.Artifact;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepository;
import com.jn.agileway.vfs.artifact.repository.LocalArtifactRepositoryLayout;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;

public class Maven2LocalRepositoryLayout extends LocalArtifactRepositoryLayout {
    public Maven2LocalRepositoryLayout(){
        setName("m2local");
    }

    @Override
    public String toRelativePath(ArtifactRepository repository, Artifact artifact) {
        String relativePath = "";
        relativePath = addSegment(relativePath, Strings.replace(artifact.getGroupId(),".","/"));
        relativePath = addSegment(relativePath, artifact.getArtifactId());
        if (Strings.isNotEmpty(artifact.getVersion())) {
            relativePath = addSegment(relativePath, artifact.getVersion());
            relativePath = addSegment(relativePath, artifact.getArtifactId() + "-" + artifact.getVersion() + (Objs.isEmpty(artifact.getClassifier()) ? "" : ("." + artifact.getClassifier())) + "." + artifact.getExtension());
        }
        return relativePath;
    }

}
