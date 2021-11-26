package com.jn.shelltools.core;

import com.jn.agileway.vfs.artifact.Artifact;
import com.jn.agileway.vfs.artifact.IGAV;
import com.jn.langx.Filter;

import java.util.Map;

public interface PackageScanner{
    /**
     * 从指定的path下扫描artifacts
     *
     * @param filter
     * @return
     */
    Map<IGAV, PackageArtifact> scan(String path, Filter<Artifact> filter);
}
