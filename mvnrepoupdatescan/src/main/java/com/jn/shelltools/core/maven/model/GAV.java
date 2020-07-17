package com.jn.shelltools.core.maven.model;

import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objects;
import com.jn.langx.util.hash.HashCodeBuilder;

public class GAV implements Comparable<GAV>{
    private String groupId;
    private String artifactId;
    private String version;

    public GAV(){}

    public GAV(String groupId, String artifactId, String version){
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return StringTemplates.formatWithPlaceholder("{}:{}:{}", groupId, artifactId, version);
    }

    public String getPomPath(){
        return getLikeUnixPath()+".pom";
    }

    public String getJarPath(){
        return getLikeUnixPath()+".jar";
    }
    public String getTestsPath(){
        return getLikeUnixPath()+"-tests.jar";
    }

    public String getSourcesPath(){
        return getLikeUnixPath()+"-sources.jar";
    }

    public String getJavaDocPath(){
        return getLikeUnixPath()+"-javadoc.jar";
    }

    private String getLikeUnixPath(){
        return StringTemplates.format("{0}/{1}/{2}/{1}-{2}", groupId.replace(".","/"), artifactId, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GAV)) return false;

        GAV gav = (GAV) o;

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
    public int hashCode() {
        return new HashCodeBuilder().with(groupId).with(artifactId).with(version).build();
    }

    @Override
    public int compareTo(GAV o) {
        return toString().compareTo(o.toString());
    }
}
