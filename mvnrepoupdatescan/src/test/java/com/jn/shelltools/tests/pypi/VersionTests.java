package com.jn.shelltools.tests.pypi;

import com.jn.shelltools.supports.pypi.versionspecifier.VersionSpecifiers;
import org.junit.Test;

import java.util.regex.Matcher;

public class VersionTests {

    @Test
    public void versionValid() {

        Matcher matcher = VersionSpecifiers.VERSION_PATTERN.matcher("1.02232.2323.a23.rev2.dev+x86");
        if (matcher.matches()) {
            System.out.println("epoch:\t\t"+matcher.group("epoch"));
            System.out.println("release:\t"+matcher.group("release"));
            System.out.println("pre:\t\t"+matcher.group("pre"));
            System.out.println("preLabel:\t\t"+matcher.group("preLabel"));
            System.out.println("preN:\t\t"+matcher.group("preN"));
            System.out.println("post:\t\t"+matcher.group("post"));
            System.out.println("postLabel:\t\t"+matcher.group("postLabel"));
            System.out.println("postN:\t\t"+matcher.group("postN"));
            System.out.println("dev:\t\t"+matcher.group("dev"));
            System.out.println("local:\t\t"+matcher.group("local"));
        }
    }


}
