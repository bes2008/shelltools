package com.jn.shelltools.core.pypi.versionspecifier.predicates;


import com.jn.shelltools.core.pypi.versionspecifier.VersionPredicate;

/**
 * !=
 */
public class VersionExclusionPredicate extends VersionPredicate {
    private VersionMatchingPredicate delegate;

    public VersionExclusionPredicate(String excepted, boolean isPrefix) {
        super(excepted);
        this.delegate = new VersionMatchingPredicate(excepted, isPrefix);
    }

    @Override
    public boolean test(String s) {
        return !this.delegate.test(s);
    }
}
