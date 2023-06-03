package com.jn.shelltools.supports.maven.model;

import com.jn.langx.util.enums.base.CommonEnum;
import com.jn.langx.util.enums.base.EnumDelegate;

public enum Packaging implements CommonEnum {
    POM("import", 1), JAR("import", 2), IMPORT("import", 3);

    private EnumDelegate delegate;

    Packaging(String name, int code) {
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
