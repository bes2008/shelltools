package com.jn.shelltools.core.pypi.versionspecifier.predicates;

import com.jn.shelltools.core.pypi.versionspecifier.VersionPredicate;

/**
 * ==
 */
public class VersionMatchingPredicate extends VersionPredicate {
    private boolean isPrefix;

    public VersionMatchingPredicate(String excepted, boolean isPrefix) {
        super(excepted);
        this.isPrefix = isPrefix;
    }

    @Override
    public boolean test(String s) {
        if (isPrefix) {
            return s.startsWith(excepted);
        } else {
            return s.equals(excepted) || s.equals(excepted + ".0");
        }
    }
}
