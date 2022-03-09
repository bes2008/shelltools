package com.jn.shelltools.supports.pypi.versionspecifier.predicates;

import com.jn.shelltools.supports.pypi.versionspecifier.VersionPredicate;

/**
 * ==
 */
public class VersionMatchingPredicate extends VersionPredicate {
    private boolean isPrefix;
    private boolean isNon;

    public VersionMatchingPredicate(String excepted, boolean isNon, boolean isPrefix) {
        super(excepted);
        this.isPrefix = isPrefix;
        this.isNon = isNon;
    }

    @Override
    public boolean test(String s) {
        boolean result = doTest(s);
        return isNon ? !result : result;
    }

    private boolean doTest(String s) {
        if (isPrefix) {
            return s.startsWith(excepted);
        } else {
            return s.equals(excepted) || s.equals(excepted + ".0");
        }
    }
}
