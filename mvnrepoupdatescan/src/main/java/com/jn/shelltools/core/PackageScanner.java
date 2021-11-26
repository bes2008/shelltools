package com.jn.shelltools.core;

import com.jn.agileway.vfs.artifact.Artifact;
import com.jn.langx.Filter;
import com.jn.shelltools.core.maven.model.MavenGAV;

import java.util.Map;

public interface PackageScanner{
    /**
     * 从指定的path下扫描artifacts
     *
     * @param filter
     * @return
     */
    Map<MavenGAV, Artifact> scan(String path, Filter<Artifact> filter);
}
