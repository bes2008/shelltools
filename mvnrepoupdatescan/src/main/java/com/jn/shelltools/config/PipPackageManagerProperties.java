package com.jn.shelltools.config;

import com.jn.agileway.feign.HttpConnectionProperties;

import java.util.List;

public class PipPackageManagerProperties {
    private HttpConnectionProperties server;
    private String destination;
    private List<String> sources;

    public HttpConnectionProperties getServer() {
        return server;
    }

    public void setServer(HttpConnectionProperties server) {
        this.server = server;
    }

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
