package com.jn.shelltools.supports.maven.model;

import com.jn.shelltools.core.PackageGAV;

import java.util.List;

public class PomModel extends PackageGAV {
    private MavenGAV parent;
    private String description;
    private String name;
    private Packaging packaging;
    private List<DependencyModel> dependencies;

    public PomModel() {

    }

    public PomModel(String groupId, String artifactId, String version) {
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
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

    public Packaging getPackaging() {
        return packaging;
    }

    public void setPackaging(Packaging packaging) {
        this.packaging = packaging;
    }

    public List<DependencyModel> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyModel> dependencies) {
        this.dependencies = dependencies;
    }
}
