package com.jn.shelltools.core.pypi.versionspecifier;


import com.jn.langx.util.function.Predicate;

public abstract class VersionPredicate implements Predicate<String> {
    protected String excepted;

    public VersionPredicate(String excepted){
        this.excepted = excepted;
    }
}
