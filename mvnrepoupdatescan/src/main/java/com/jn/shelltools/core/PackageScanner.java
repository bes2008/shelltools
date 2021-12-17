package com.jn.shelltools.core;

import com.jn.langx.Filter;

import java.util.Map;

public interface PackageScanner{
    /**
     * 从指定的path下扫描artifacts
     *
     * @param relativePath 扫描路径，仓库的相对路径，可以为null, 若为空则代表从仓库的 root directory进行扫描
     * @param filter 扫描时过滤器
     * @return 返回搜索到的包
     */
    Map<PackageGAV, PackageArtifact> scan(String relativePath, Filter<PackageArtifact> filter);
}
