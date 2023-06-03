package com.jn.shelltools.supports.maven.model;

import com.jn.langx.annotation.Nullable;
import com.jn.shelltools.core.PackageGAV;

import java.util.List;

public class DependencyModel extends PackageGAV {
    @Nullable
    private String projectName;
    private boolean optional = false;
    private List<PackageGAV> excludes;
    private Packaging packaging = Packaging.JAR;

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

    public Packaging getPackaging() {
        return packaging;
    }

    public void setPackaging(Packaging packaging) {
        this.packaging = packaging;
    }

    public DependencyModel(){

    }

    public DependencyModel(String groupId, String artifactId, String version){
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }

    public List<PackageGAV> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<PackageGAV> excludes) {
        this.excludes = excludes;
    }
}
