package com.jn.shelltools.core.pypi.model.summary;

import java.util.List;
import java.util.Map;

/**
 * https://pypi.org/pypi/<package>/json
 */
public class PipPackageSummary {
    private PipPackageInfo info;
    private int last_serial;
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
