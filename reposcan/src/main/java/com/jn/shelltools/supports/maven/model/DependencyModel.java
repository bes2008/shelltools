package com.jn.shelltools.supports.maven.model;

import com.jn.langx.annotation.Nullable;
import com.jn.shelltools.core.PackageGAV;

import java.util.List;

public class DependencyModel extends PackageGAV {
    @Nullable
    private String projectName;
    private boolean optional = false;
    private List<PackageGAV> exclusions;
    private Packaging type;
    private DependencyScope scope;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public Packaging getType() {
        return type;
    }

    public void setType(Packaging type) {
        this.type = type;
    }

    public DependencyModel(){

    }

    public DependencyModel(String groupId, String artifactId, String version){
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }

    public List<PackageGAV> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<PackageGAV> exclusions) {
        this.exclusions = exclusions;
    }

    public DependencyScope getScope() {
        return scope;
    }

    public void setScope(DependencyScope scope) {
        this.scope = scope;
    }
}
