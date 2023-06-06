package com.jn.shelltools.supports.maven;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.agileway.vfs.artifact.ArtifactManagerAware;
import com.jn.agileway.vfs.utils.FileObjects;
import com.jn.langx.util.logging.Loggers;
import com.jn.langx.util.net.URLs;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import org.apache.commons.vfs2.FileObject;
import org.slf4j.Logger;

import java.io.File;
import java.net.URL;

public class MavenPackageManager implements ArtifactManagerAware {
    private static final Logger logger = Loggers.getLogger(MavenPackageManager.class);
    private ArtifactManager artifactManager;

    public File getPomFile(PackageGAV gav) {
        MavenPackageArtifact artifact = new MavenPackageArtifact(gav.getGroupId(), gav.getArtifactId(), gav.getVersion());
        artifact.setSupportSynchronized(true);
        artifact.setExtension("pom");

        try {
            FileObject fileObject = artifactManager.getArtifactFile(artifact);
            if (FileObjects.isExists(fileObject)) {
                if (fileObject.isFile()) {
                    URL url = fileObject.getURL();
                    return URLs.getFile(url);
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public MavenPackageArtifact parsePom(MavenPomParser.Builder builder, PackageGAV pomGav) {
        File pomXml = getPomFile(pomGav);
        return MavenPomParser.parsePom(builder, pomXml);
    }

    @Override
    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    @Override
    public ArtifactManager getArtifactManager() {
        return artifactManager;
    }
}
