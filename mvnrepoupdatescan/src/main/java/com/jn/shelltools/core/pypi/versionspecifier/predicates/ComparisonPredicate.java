package com.jn.shelltools.core.pypi.versionspecifier.predicates;

import com.jn.langx.util.function.Predicate;

public class ComparisonPredicate implements Predicate<String> {
    private String version;
    private boolean inclusive;
    private boolean lessThan;

    @Override
    public boolean test(String s) {
        return false;
    }

    
}
