package com.jn.shelltools.supports.maven.model;

import java.util.List;

public class DependencyManagement {
    private List<Dependency> dependencies;

    public DependencyManagement() {

    }

    public DependencyManagement(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }
}
