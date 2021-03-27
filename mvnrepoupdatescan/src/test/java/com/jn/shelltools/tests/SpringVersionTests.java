package com.jn.shelltools.tests;

import com.jn.langx.util.Strings;

public class SpringVersionTests {
    public static String getFakeVersion() {
        String version = "4.3.25.RELEASE";
        if (Strings.endsWithIgnoreCase(version, ".RELEASE")) {
            version = version.substring(0, version.length() - ".RELEASE".length()).toString();
        }
        return version;
    }

    public static void main(String[] args) {
        System.out.println(getFakeVersion());
    }
}
