package com.jn.shelltools.supports.maven.model;

import com.jn.agileway.vfs.artifact.IGAV;
import com.jn.langx.text.StringTemplates;
import com.jn.shelltools.core.PackageGAV;

public class MavenGAV extends PackageGAV implements Comparable<MavenGAV>, IGAV {

    public MavenGAV(){}

    public MavenGAV(String groupId, String artifactId, String version){
        setGroupId(groupId);
        setArtifactId(artifactId);
        setVersion(version);
    }


    @Override
    public String toString() {
        return StringTemplates.formatWithPlaceholder("{}:{}:{}", getGroupId(), getArtifactId(), getVersion());
    }

    /**
     * @return pom文件所在的本地仓库的相对路径
     */
    public String getLocation(){
       return StringTemplates.format("{0}/{1}/{2}", getGroupId().replace(".","/"), getArtifactId(), getVersion());
    }

    /**
     * @return 获取 pom 文件的相对路径，相对于仓库根目录
     */
    public String getPomPath(){
        return getLikeUnixPath()+".pom";
    }

    /**
     * @return 获取 jar 文件的相对路径，相对于仓库根目录
     */
    public String getJarPath(){
        return getLikeUnixPath()+".jar";
    }

    /**
     * @return 获取 tests 文件的相对路径，相对于仓库根目录
     */
    public String getTestsPath(){
        return getLikeUnixPath()+"-tests.jar";
    }

    /**
     * @return 获取 sources 文件的相对路径，相对于仓库根目录
     */
    public String getSourcesPath(){
        return getLikeUnixPath()+"-sources.jar";
    }

    /**
     * @return 获取 javadoc 文件的相对路径，相对于仓库根目录
     */
    public String getJavaDocPath(){
        return getLikeUnixPath()+"-javadoc.jar";
    }

    private String getLikeUnixPath(){
        return StringTemplates.format("{0}/{1}/{2}/{1}-{2}", getGroupId().replace(".","/"), getArtifactId(), getVersion());
    }



    @Override
    public int compareTo(MavenGAV o) {
        return toString().compareTo(o.toString());
    }
}
