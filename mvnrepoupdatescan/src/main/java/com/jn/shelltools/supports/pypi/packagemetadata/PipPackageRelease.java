package com.jn.shelltools.supports.pypi.packagemetadata;

public class PipPackageRelease {
    private String comment_text;
    private PipPackageDigests digests;
    private int downloads;
    private String filename;
    private boolean has_sig;
    private String md5_digest;
    private String packagetype;
    private String python_version;
    private String requires_python;
    private long size;
    private String upload_time;
    private String upload_time_iso_8601;
    private String url;
    private boolean yanked;
    private String yanked_reason;

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public PipPackageDigests getDigests() {
        return digests;
    }

    public void setDigests(PipPackageDigests digests) {
        this.digests = digests;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isHas_sig() {
        return has_sig;
    }

    public void setHas_sig(boolean has_sig) {
        this.has_sig = has_sig;
    }

    public String getMd5_digest() {
        return md5_digest;
    }

    public void setMd5_digest(String md5_digest) {
        this.md5_digest = md5_digest;
    }

    public String getPackagetype() {
        return packagetype;
    }

    public void setPackagetype(String packagetype) {
        this.packagetype = packagetype;
    }

    public String getPython_version() {
        return python_version;
    }

    public void setPython_version(String python_version) {
        this.python_version = python_version;
    }

    public String getRequires_python() {
        return requires_python;
    }

    public void setRequires_python(String requires_python) {
        this.requires_python = requires_python;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getUpload_time_iso_8601() {
        return upload_time_iso_8601;
    }

    public void setUpload_time_iso_8601(String upload_time_iso_8601) {
        this.upload_time_iso_8601 = upload_time_iso_8601;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isYanked() {
        return yanked;
    }

    public void setYanked(boolean yanked) {
        this.yanked = yanked;
    }

    public String getYanked_reason() {
        return yanked_reason;
    }

    public void setYanked_reason(String yanked_reason) {
        this.yanked_reason = yanked_reason;
    }
}
