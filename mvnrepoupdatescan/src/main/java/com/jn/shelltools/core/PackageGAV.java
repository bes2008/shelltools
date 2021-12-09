package com.jn.shelltools.core;

import com.jn.agileway.vfs.artifact.IGAV;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objs;
import com.jn.langx.util.hash.HashCodeBuilder;
import com.jn.shelltools.supports.maven.model.MavenGAV;

import java.util.Objects;

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
        if (this == o){
            return true;
        }
        if(o.getClass()!=PackageGAV.class){
            return false;
        }

        PackageGAV gav = (PackageGAV) o;

        return Objs.equals(toString(), gav.toString());
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
