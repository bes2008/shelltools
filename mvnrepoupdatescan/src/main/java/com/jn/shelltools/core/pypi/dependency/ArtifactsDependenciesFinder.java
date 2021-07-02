package com.jn.shelltools.core.pypi.dependency;

import com.jn.langx.util.struct.Pair;
import com.jn.shelltools.core.pypi.PypiArtifact;

import java.util.List;

/**
 * 根据同一个版本的多种 artifacts 查找依赖
 */
public interface ArtifactsDependenciesFinder extends DependenciesFinder<Pair<String, List<PypiArtifact>>> {
    @Override
    List<String> get(Pair<String, List<PypiArtifact>> input);
}
