package com.jn.shelltools.supports.pypi.versionspecifier.predicates;

import com.jn.shelltools.supports.pypi.versionspecifier.VersionPredicate;

/**
 * ===
 */
public class EqualsPredicate extends VersionPredicate {
    public EqualsPredicate(String excepted) {
        super(excepted);
    }

    @Override
    public boolean test(String s) {
        return this.excepted.equals(s);
    }
}
