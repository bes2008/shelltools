package com.jn.shelltools.supports.pypi.dependency;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.langx.util.Objs;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.struct.Pair;
import com.jn.shelltools.supports.pypi.PypiArtifact;

import java.util.List;
import java.util.Map;

public class DefaultArtifactsDependenciesFinder implements ArtifactsDependenciesFinder {

    /**
     * key: file extension
     * value: finder
     */
    private Map<String, ArtifactDependenciesFinder> delegates = Collects.emptyHashMap();

    private ArtifactManager artifactManager;

    public void setArtifactManager(final ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
        Pipeline.of(delegates.values())
                .forEach(new Consumer<ArtifactDependenciesFinder>() {
                    @Override
                    public void accept(ArtifactDependenciesFinder artifactDependenciesFinder) {
                        artifactDependenciesFinder.setArtifactManager(artifactManager);
                    }
                });
    }

    public DefaultArtifactsDependenciesFinder() {
        addArtifactDependenciesFinder(new SourceArtifactDependenciesFinder());
        addArtifactDependenciesFinder(new WheelArtifactDependenciesFinder());
    }

    @Override
    public ArtifactManager getArtifactManager() {
        return artifactManager;
    }

    private List<String> invalid_chars = Collects.newArrayList("[", "]");

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
                    Collects.forEach(deps, new Consumer<String>() {
                        @Override
                        public void accept(String dep) {
                            if (Collects.noneMatch(invalid_chars, new Predicate<String>() {
                                @Override
                                public boolean test(String invalid) {
                                    return dep.contains(invalid);
                                }
                            })) {
                                dependencies.add(dep);
                            } else {
                                System.out.printf("invalid dep: " + dep);
                            }
                        }
                    });
                }
            }
        });
        return Collects.asList(Collects.asSet(dependencies));
    }

    public void addArtifactDependenciesFinder(ArtifactDependenciesFinder finder) {
        finder.setArtifactManager(getArtifactManager());
        Collects.forEach(finder.supportedExtensions(), new Consumer<String>() {
            @Override
            public void accept(String extension) {
                delegates.put(extension, finder);
            }
        });
    }

}
