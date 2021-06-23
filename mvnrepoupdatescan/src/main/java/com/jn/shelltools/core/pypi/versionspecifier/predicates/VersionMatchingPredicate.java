package com.jn.shelltools.core.pypi.versionspecifier.predicates;

import com.jn.langx.util.function.Predicate;

/**
 * ==
 */
public class VersionMatchingPredicate implements Predicate<String> {
    private String version;
    private boolean isPrefix;

    public VersionMatchingPredicate(String version, boolean isPrefix) {
        this.version = version;
        this.isPrefix = isPrefix;
    }

    @Override
    public boolean test(String s) {
        if (isPrefix) {
            return s.startsWith(version);
        } else {
            return s.equals(version) || s.equals(version + ".0");
        }
    }
}
