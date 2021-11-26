package com.jn.shelltools.core.maven.model;

import com.jn.agileway.vfs.artifact.AbstractArtifact;

import java.util.List;

public class MavenArtifact extends AbstractArtifact {
    private MavenGAV parent;
    private String description;
    private String name;
    private Packaging packaging;
    private DependencyScope scope;
    private long lastModifiedTime;
    private List<MavenArtifact> dependencies;
    private List<License> licenses;
    /**
     * pom 文件所在的本地仓库的目录
     */
    private String localPath;

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

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public List<MavenArtifact> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<MavenArtifact> dependencies) {
        this.dependencies = dependencies;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

}
