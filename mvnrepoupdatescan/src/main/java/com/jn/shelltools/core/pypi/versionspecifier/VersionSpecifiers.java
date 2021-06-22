package com.jn.shelltools.core.pypi.versionspecifier;

import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Predicate;

import java.util.List;

/**
 * 参考官方规范：
 * https://www.python.org/dev/peps/pep-0345/#version-specifiers
 * https://www.python.org/dev/peps/pep-0440/
 */
public class VersionSpecifiers {
    public static final String VERSION_EXP_COMPATIBLE_RELEASE = "~=";
    public static final String VERSION_EXP_VERSION_EXCLUSION = "!=";
    public static final String VERSION_EXP_INCLUSIVE_COMPARISON_LESS_THAN = "<=";
    public static final String VERSION_EXP_INCLUSIVE_COMPARISON_GREAT_THAN = ">=";
    public static final String VERSION_EXP_EXCLUSIVE_COMPARISON_LESS_THAN = "<";
    public static final String VERSION_EXP_EXCLUSIVE_COMPARISON_GREAT_THAN = ">";
    public static final String VERSION_EXP_ARBITRARY_EQUALITY = "===";
    public static final String VERSION_EXP_VERSION_MATCHING = "==";

    public static final List<String> VERSION_EXP_SPECIFIERS = Collects.newArrayList(
            VERSION_EXP_COMPATIBLE_RELEASE,
            VERSION_EXP_VERSION_EXCLUSION,
            VERSION_EXP_INCLUSIVE_COMPARISON_LESS_THAN,
            VERSION_EXP_INCLUSIVE_COMPARISON_GREAT_THAN,
            VERSION_EXP_EXCLUSIVE_COMPARISON_LESS_THAN,
            VERSION_EXP_EXCLUSIVE_COMPARISON_GREAT_THAN,
            VERSION_EXP_ARBITRARY_EQUALITY,
            VERSION_EXP_VERSION_MATCHING
    );

    public static final boolean isOmitSpecifier(String expression) {
        return Collects.allMatch(VERSION_EXP_SPECIFIERS, new Predicate<String>() {
            @Override
            public boolean test(String specifier) {
                return !expression.startsWith(specifier);
            }
        });
    }

    // https://www.python.org/dev/peps/pep-0440/#version-scheme
    private static final String VERSION_SEG_EPOCH = "\\d!";
    private static final String VERSION_SEG_RELEASE = "\\d(.\\d)*";
    private static final String VERSION_SEG_PRE = "(a|alpha|b|beta|rc|c)\\d";
    private static final String VERSION_SEG_POST = "post\\d";
    private static final String VERSION_SEG_DEV = "dev\\d";
    public static final String VERSION_PATTERN = "(" + VERSION_SEG_EPOCH + ")?" + VERSION_SEG_RELEASE + "(" + VERSION_SEG_PRE + ")?" + "(" + VERSION_SEG_POST + ")?" + "(" + VERSION_SEG_DEV + ")?";

}
