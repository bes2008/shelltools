package com.jn.shelltools.supports.maven.model;

import com.jn.langx.util.enums.base.CommonEnum;
import com.jn.langx.util.enums.base.EnumDelegate;

public enum DependencyScope implements CommonEnum {
    COMPILE("compile", 1),
    RUNTIME("runtime", 2),
    TEST("test", 3),
    IMPORT("import", 4),
    PROVIDER("provider", 5),
    System("system", 6);

    private EnumDelegate delegate;

    DependencyScope(String name, int code) {
        this.delegate = new EnumDelegate(code, name, name);
    }


    @Override
    public int getCode() {
        return this.delegate.getCode();
    }

    @Override
    public String getDisplayText() {
        return this.delegate.getDisplayText();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }
}
