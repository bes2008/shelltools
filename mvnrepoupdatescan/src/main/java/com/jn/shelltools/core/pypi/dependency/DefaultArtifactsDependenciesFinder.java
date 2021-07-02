package com.jn.shelltools.core.pypi.dependency;

import com.jn.langx.util.Objs;
import com.jn.langx.util.struct.Pair;
import com.jn.shelltools.core.pypi.PypiArtifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultArtifactsDependenciesFinder implements ArtifactsDependenciesFinder {
    private static final Logger logger = LoggerFactory.getLogger(DefaultArtifactsDependenciesFinder.class);

    @Override
    public List<String> get(Pair<String, List<PypiArtifact>> versionArtifactsPair) {
        List<PypiArtifact> artifacts = versionArtifactsPair.getValue();
        if (Objs.isEmpty(artifacts)) {

        }
        return null;
    }
}
