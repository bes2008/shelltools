package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.vfs.artifact.ArtifactManagerAware;
import com.jn.shelltools.core.pypi.PypiArtifact;

import java.util.List;

/**
 * 基于单个 artifact 来进行依赖查找
 *
 * @see SourceArtifactDependenciesFinder
 * @see WheelArtifactDependenciesFinder
 */
public interface ArtifactDependenciesFinder extends DependenciesFinder<PypiArtifact>, ArtifactManagerAware {
    @Override
    List<String> get(PypiArtifact artifact);

    /**
     * @return 返回支持的 artifact 的扩展名列表
     */
    List<String> supportedExtensions();
}
