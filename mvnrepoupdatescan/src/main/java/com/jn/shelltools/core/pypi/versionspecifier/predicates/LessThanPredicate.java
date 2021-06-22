package com.jn.shelltools.core.pypi.versionspecifier.predicates;

import com.jn.langx.util.function.Predicate;

public class LessThanPredicate implements Predicate<String> {
    private String version;
    private boolean inclusive;

    @Override
    public boolean test(String s) {
        return false;
    }
}
