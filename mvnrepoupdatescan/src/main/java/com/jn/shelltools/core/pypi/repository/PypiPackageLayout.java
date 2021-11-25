package com.jn.shelltools.core.pypi.repository;

import com.jn.agileway.vfs.artifact.Artifact;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepository;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryLayout;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.shelltools.core.pypi.PypiArtifact;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageRelease;

/**
 * Pypi 官方介质下载地址的 布局方式
 */
public class PypiPackageLayout implements ArtifactRepositoryLayout {

    public String getPath(ArtifactRepository repository, Artifact art) {
        Preconditions.checkTrue(art instanceof PypiArtifact);
        PypiArtifact artifact = (PypiArtifact) art;
        PipPackageRelease release = artifact.getRelease();
        return release.getUrl();
    }

    public String getDigitPath(ArtifactRepository repository, Artifact art, String digit) {
        Preconditions.checkTrue(art instanceof PypiArtifact);
        PypiArtifact artifact = (PypiArtifact) art;
        PipPackageRelease release = artifact.getRelease();
        if (Strings.equalsIgnoreCase("md5", digit)) {
            String md5 = release.getMd5_digest();
            if (Objs.isEmpty(md5)) {
                if (release.getDigests() != null) {
                    md5 = release.getDigests().getMd5();
                }
            }
            return md5;
        } else if (Strings.equalsIgnoreCase("sha256", digit)) {
            if (release.getDigests() != null) {
                return release.getDigests().getSha256();
            }
        }
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public String getName() {
        return "pypi";
    }
}
