package com.jn.shelltools.supports.maven.model;

import com.jn.shelltools.core.PackageGAV;

import java.util.List;
import java.util.Map;

public class PomModel extends PackageGAV {
    private MavenGAV parent;
    private String description;
    private String name;
    private Packaging packaging = Packaging.JAR;
    private List<DependencyModel> dependencies;
    private Map<String, String> properties;

    public PomModel() {

    }

    public PomModel(String groupId, String artifactId, String version) {
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
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
