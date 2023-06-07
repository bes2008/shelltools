package com.jn.shelltools.supports.maven.model;

import com.jn.shelltools.core.LocalPackageArtifact;

import java.util.List;
import java.util.Map;

/**
 * maven java 包的一个发布情况
 */
public class MavenPackageArtifact extends LocalPackageArtifact {
    private MavenGAV parent;
    private String description;
    private String name;
    private Packaging packaging;
    private DependencyScope scope;
    private DependencyManagement dependencyManagement;
    private List<Dependency> dependencies;
    private List<License> licenses;

    private Map<String, String> properties;

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


    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public DependencyManagement getDependencyManagement() {
        return dependencyManagement;
    }

    public void setDependencyManagement(List<Dependency> dependencies) {
        setDependencyManagement(new DependencyManagement(dependencies));
    }

    public void setDependencyManagement(DependencyManagement dependencyManagement) {
        this.dependencyManagement = dependencyManagement;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
