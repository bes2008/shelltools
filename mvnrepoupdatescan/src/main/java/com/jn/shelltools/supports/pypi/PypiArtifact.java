package com.jn.shelltools.supports.pypi;

import com.jn.agileway.vfs.artifact.AbstractArtifact;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;
import com.jn.shelltools.supports.pypi.packagemetadata.PipPackageRelease;


public class PypiArtifact extends AbstractArtifact {
    private PipPackageRelease release;

    public PipPackageRelease getRelease() {
        return release;
    }

    public void setRelease(PipPackageRelease release) {
        this.release = release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PypiArtifact that = (PypiArtifact) o;
        if (!Objs.equals(getGroupId(), that.getGroupId())) {
            return false;
        }

        if (!Objs.equals(getArtifactId(), that.getArtifactId())) {
            return false;
        }

        if (!Objs.equals(getVersion(), that.getVersion())) {
            return false;
        }

        if (!Objs.equals(getClassifier(), that.getClassifier())) {
            return false;
        }

        if (!Objs.equals(getExtension(), that.getExtension())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objs.hash(getGroupId(), getArtifactId(), getVersion(), getClassifier(), getExtension());
    }

    @Override
    public String toString() {
        if (Strings.isBlank(getClassifier())) {
            return StringTemplates.formatWithPlaceholder("{}:{}:{}.{}", getGroupId(), getArtifactId(), getVersion(), getExtension());
        } else {
            return StringTemplates.formatWithPlaceholder("{}:{}:{}-{}.{}", getGroupId(), getArtifactId(), getVersion(), getClassifier(), getExtension());
        }
    }
}
