package com.jn.shelltools.supports.pypi.dependency;

import com.jn.agileway.vfs.artifact.ArtifactManagerAware;
import com.jn.langx.util.struct.Pair;
import com.jn.shelltools.supports.pypi.PypiArtifact;

import java.util.List;
import java.util.Set;

/**
 * 根据同一个版本的多种 artifacts 查找依赖
 */
public interface ArtifactsDependenciesFinder extends DependenciesFinder<Pair<String, Set<PypiArtifact>>>, ArtifactManagerAware {
    @Override
    List<String> get(Pair<String, Set<PypiArtifact>> input);
}
