package com.jn.shelltools.supports.maven;

import java.util.List;

public class MavenPackageManagerProperties {
    private String destination;
    private List<String> sources;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }
}
