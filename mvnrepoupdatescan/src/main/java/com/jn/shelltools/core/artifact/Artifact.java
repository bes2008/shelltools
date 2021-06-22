package com.jn.shelltools.core.artifact;

import com.jn.shelltools.core.IGAV;

public interface Artifact extends IGAV {
    String getClassifier();
    void setClassifier(String classifier);

    /**
     * 文件扩展名
     * @return
     */
    String getExtension();
    void setExtension();
}
