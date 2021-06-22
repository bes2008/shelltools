package com.jn.shelltools.core.pypi.model.summary;


import java.util.List;
import java.util.Map;

public class PipPackageInfo {
    private String author;
    private String author_email;
    private String bugtrack_url;
    private List<String> classifiers;
    private String description;
    private String description_content_type;
    private String docs_url;
    private String download_url;
    private List<PipPackageDownload> downloads;
    private String home_page;
    private String keywords;
    private String license;
    private String maintainer;
    private String maintainer_email;
    private String name;
    private String package_url;
    private String platform;
    private String project_url;
    private Map<String,String> project_urls;
    private String release_url;
    private List<String> requires_dist;
    private String requires_python;
    private String summary;
    private String version;
    private boolean yanked;
    private String yanked_reason;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getBugtrack_url() {
        return bugtrack_url;
    }

    public void setBugtrack_url(String bugtrack_url) {
        this.bugtrack_url = bugtrack_url;
    }

    public List<String> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(List<String> classifiers) {
        this.classifiers = classifiers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_content_type() {
        return description_content_type;
    }

    public void setDescription_content_type(String description_content_type) {
        this.description_content_type = description_content_type;
    }

    public String getDocs_url() {
        return docs_url;
    }

    public void setDocs_url(String docs_url) {
        this.docs_url = docs_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public List<PipPackageDownload> getDownloads() {
        return downloads;
    }

    public void setDownloads(List<PipPackageDownload> downloads) {
        this.downloads = downloads;
    }

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    public String getMaintainer_email() {
        return maintainer_email;
    }

    public void setMaintainer_email(String maintainer_email) {
        this.maintainer_email = maintainer_email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackage_url() {
        return package_url;
    }

    public void setPackage_url(String package_url) {
        this.package_url = package_url;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProject_url() {
        return project_url;
    }

    public void setProject_url(String project_url) {
        this.project_url = project_url;
    }

    public String getRelease_url() {
        return release_url;
    }

    public void setRelease_url(String release_url) {
        this.release_url = release_url;
    }

    public String getRequires_python() {
        return requires_python;
    }

    public void setRequires_python(String requires_python) {
        this.requires_python = requires_python;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Map<String, String> getProject_urls() {
        return project_urls;
    }

    public void setProject_urls(Map<String, String> project_urls) {
        this.project_urls = project_urls;
    }

    public List<String> getRequires_dist() {
        return requires_dist;
    }

    public void setRequires_dist(List<String> requires_dist) {
        this.requires_dist = requires_dist;
    }
}
