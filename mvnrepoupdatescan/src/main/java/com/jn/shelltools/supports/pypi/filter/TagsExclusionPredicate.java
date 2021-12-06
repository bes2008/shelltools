package com.jn.shelltools.supports.pypi.filter;

import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Predicate;
import com.jn.shelltools.supports.pypi.PypiArtifact;

import java.util.List;

public class TagsExclusionPredicate implements Predicate<PypiArtifact> {
    private List<String> tags;

    public TagsExclusionPredicate() {

    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(PypiArtifact artifact) {
        if (Objs.isEmpty(tags)) {
            return true;
        }
        String classifier = artifact.getClassifier();
        if (Strings.isNotBlank(classifier)) {
            return Pipeline.of(tags).allMatch(new Predicate<String>() {
                @Override
                public boolean test(String tag) {
                    return !classifier.contains(tag);
                }
            });
        } else {
            return true;
        }
    }
}
