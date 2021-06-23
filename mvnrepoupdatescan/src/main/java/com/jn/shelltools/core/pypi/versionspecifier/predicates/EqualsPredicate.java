package com.jn.shelltools.core.pypi.versionspecifier.predicates;

import com.jn.shelltools.core.pypi.versionspecifier.VersionPredicate;

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
