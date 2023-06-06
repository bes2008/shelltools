package com.jn.shelltools.supports.maven.model;

import com.jn.shelltools.core.LocalPackageArtifact;

import java.util.List;

/**
 * maven java 包的一个发布情况
 */
public class MavenPackageArtifact extends LocalPackageArtifact {
    private MavenGAV parent;
    private String description;
    private String name;
    private Packaging packaging;
    private DependencyScope scope;
    private long lastModifiedTime;
    private List<MavenPackageArtifact> dependencies;
    private List<License> licenses;

    public MavenPackageArtifact() {

    }

    public MavenPackageArtifact(String groupId, String artifactId, String version) {
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }

    public MavenGAV getGav() {
        return new MavenGAV(getGroupId(), getArtifactId(), getVersion());
    }

    public void setGav(MavenGAV gav) {
        setGroupId(gav.getGroupId());
        setArtifactId(gav.getArtifactId());
        setVersion(gav.getVersion());
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public void setPackaging(Packaging packaging) {
        this.packaging = packaging;
    }

    public DependencyScope getScope() {
        return scope;
    }

    public void setScope(DependencyScope scope) {
        this.scope = scope;
    }

    public MavenGAV getParent() {
        return parent;
    }

    public void setParent(MavenGAV parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }


    public List<MavenPackageArtifact> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<MavenPackageArtifact> dependencies) {
        this.dependencies = dependencies;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

}
