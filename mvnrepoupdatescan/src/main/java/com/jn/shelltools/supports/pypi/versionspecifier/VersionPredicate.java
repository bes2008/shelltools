package com.jn.shelltools.supports.pypi.versionspecifier;


import com.jn.langx.util.function.Predicate;

public abstract class VersionPredicate implements Predicate<String> {
    /**
     * 期望的版本
     */
    protected String excepted;

    public VersionPredicate(String excepted){
        this.excepted = excepted;
    }
}
