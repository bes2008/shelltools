package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.langx.util.Objs;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.struct.Holder;
import com.jn.langx.util.struct.Pair;
import com.jn.shelltools.core.pypi.PypiArtifact;

import java.util.List;
import java.util.Map;

public class DefaultArtifactsDependenciesFinder implements ArtifactsDependenciesFinder {

    /**
     * key: file extension
     * value: finder
     */
    private Map<String, ArtifactDependenciesFinder> delegates = Collects.emptyHashMap();

    private ArtifactManager artifactManager;

    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    @Override
    public ArtifactManager getArtifactManager() {
        return artifactManager;
    }

    @Override
    public List<String> get(Pair<String, List<PypiArtifact>> versionArtifactsPair) {
        List<PypiArtifact> artifacts = versionArtifactsPair.getValue();
        final List<String> dependencies = Collects.emptyArrayList();
        Collects.forEach(artifacts, new Consumer<PypiArtifact>() {
            @Override
            public void accept(PypiArtifact artifact) {
                String extension = artifact.getExtension();
                ArtifactDependenciesFinder delegate = delegates.get(extension);
                if (delegate != null) {
                    List<String> deps = delegate.get(artifact);
                    if (!Objs.isEmpty(deps)) {
                        dependencies.addAll(deps);
                    }
                }
            }
        });
        return Collects.asList(Collects.asSet(dependencies));
    }

    public void addArtifactDependenciesFinder(ArtifactDependenciesFinder finder) {
        Collects.forEach(finder.supportedExtensions(), new Consumer<String>() {
            @Override
            public void accept(String extension) {
                delegates.put(extension, finder);
            }
        });
    }

}
