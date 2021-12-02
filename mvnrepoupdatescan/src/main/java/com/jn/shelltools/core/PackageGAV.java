package com.jn.shelltools.core;

import com.jn.agileway.vfs.artifact.IGAV;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objects;
import com.jn.langx.util.hash.HashCodeBuilder;
import com.jn.shelltools.supports.maven.model.MavenGAV;

public class PackageGAV implements IGAV {
    private String groupId;
    private String artifactId;
    private String version;

    public PackageGAV(){

    }

    public PackageGAV(String groupId, String artifactId, String version){
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(String s) {
        this.groupId = s;
    }

    @Override
    public String getArtifactId() {
        return this.artifactId;
    }

    @Override
    public void setArtifactId(String s) {
        this.artifactId = s;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(String s) {
        this.version = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PackageGAV)) return false;

        PackageGAV gav = (PackageGAV) o;

        if (!Objects.equals(groupId, gav.groupId)) {
            return false;
        }

        if (!Objects.equals(artifactId, gav.artifactId)) {
            return false;
        }

        if (!Objects.equals(version, gav.version)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return StringTemplates.formatWithPlaceholder( "{}:{}:{}", groupId, artifactId, version);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().with(groupId).with(artifactId).with(version).build();
    }
}
