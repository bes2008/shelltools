package com.jn.shelltools.core.pypi.versionspecifier.predicates;


import java.util.function.Predicate;

/**
 * !=
 */
public class VersionExclusionPredicate implements Predicate<String> {
    private VersionMatchingPredicate delegate;

    public VersionExclusionPredicate(String version, boolean isPrefix) {
        this.delegate = new VersionMatchingPredicate(version, isPrefix);
    }

    @Override
    public boolean test(String s) {
        return !this.delegate.test(s);
    }
}
