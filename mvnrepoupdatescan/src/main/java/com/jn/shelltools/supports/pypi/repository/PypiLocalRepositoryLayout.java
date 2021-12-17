package com.jn.shelltools.supports.pypi.repository;

import com.jn.agileway.vfs.artifact.Artifact;
import com.jn.agileway.vfs.artifact.repository.AbstractArtifactRepositoryLayout;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepository;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;

/**
 * 我们自定义的 python 本地仓库布局方式
 */
public class PypiLocalRepositoryLayout extends AbstractArtifactRepositoryLayout {
    public PypiLocalRepositoryLayout() {
        setName("pypi_local");
    }

    @Override
    public String toRelativePath(ArtifactRepository artifactRepository, Artifact artifact) {
        String relativePath = "";
        // 把 artifactId 作为目录
        relativePath = this.addSegment(relativePath, artifact.getArtifactId());
        // 文件：
        if (Strings.isNotEmpty(artifact.getVersion())) {
            relativePath = this.addSegment(relativePath, artifact.getVersion());
            relativePath = this.addSegment(relativePath, artifact.getArtifactId() + "-" + artifact.getVersion() + (Objs.isEmpty(artifact.getClassifier()) ? "" : "-" + artifact.getClassifier()) + "." + artifact.getExtension());
        } else {
            relativePath = this.addSegment(relativePath, artifact.getArtifactId() + (Objs.isEmpty(artifact.getClassifier()) ? "" : "-" + artifact.getClassifier()) + "." + artifact.getExtension());
        }
        return relativePath;
    }
}
