package com.jn.shelltools.supports.pypi.filter;

import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Predicate;
import com.jn.shelltools.supports.pypi.PypiArtifact;
import com.jn.shelltools.supports.pypi.Pypis;

import java.util.Collection;

public class SourceArtifactPredicate implements Predicate<PypiArtifact> {
    private final Collection<String> extensions = Pypis.getFileExtensions(Pypis.PACKAGE_TYPE_SOURCE);

    @Override
    public boolean test(PypiArtifact packageArtifact) {
        String extension = packageArtifact.getExtension();
        return Pipeline.of(extensions).anyMatch(new Predicate<String>() {
            @Override
            public boolean test(String ext) {
                return Strings.equalsIgnoreCase(ext, extension);
            }
        });
    }
}
