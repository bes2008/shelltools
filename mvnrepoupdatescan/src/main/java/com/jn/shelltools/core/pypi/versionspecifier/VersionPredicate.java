package com.jn.shelltools.core.pypi.versionspecifier;

import java.util.function.Predicate;

public abstract class VersionPredicate implements Predicate<String> {
    protected String excepted;

    public VersionPredicate(String excepted){
        this.excepted = excepted;
    }
}
