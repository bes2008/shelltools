package com.jn.shelltools.supports.pypi.packagemetadata;

import java.util.List;
import java.util.Map;

/**
 * 包元数据 规范文档：
 * https://www.python.org/dev/peps/pep-0345
 *
 * 获取包元数据的 链接：
 * https://pypi.org/pypi/${package}/json
 */
public class PipPackageMetadata {
    private PipPackageInfo info;
    private int last_serial;
    /**
     * key: version
     * value:  不同类型的介质
     */
    private Map<String, List<PipPackageRelease>> releases;
    private List<PipPackageRelease> urls;

    public PipPackageInfo getInfo() {
        return info;
    }

    public void setInfo(PipPackageInfo info) {
        this.info = info;
    }

    public int getLast_serial() {
        return last_serial;
    }

    public void setLast_serial(int last_serial) {
        this.last_serial = last_serial;
    }

    public Map<String, List<PipPackageRelease>> getReleases() {
        return releases;
    }

    public void setReleases(Map<String, List<PipPackageRelease>> releases) {
        this.releases = releases;
    }

    public List<PipPackageRelease> getUrls() {
        return urls;
    }

    public void setUrls(List<PipPackageRelease> urls) {
        this.urls = urls;
    }
}
