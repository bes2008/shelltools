package com.jn.shelltools.core.pypi.versionspecifier;

import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Predicate;

import java.util.List;
import java.util.regex.Pattern;

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
    // https://www.pythonheidong.com/blog/article/187997/31fe90bd992afcd027a1/
    private static final String VERSION_SEG_EPOCH = "\\d+!";
    private static final String VERSION_SEG_RELEASE = "\\d+(\\.\\d+)*";
    private static final String VERSION_SEG_PRE = "[-_.]?(a|alpha|b|beta|rc|c|pre|preview)([-_.]?\\d+)?";
    private static final String VERSION_SEG_POST = "[-_.]?(post|rev|r)([-_.]?\\d+)?";
    private static final String VERSION_SEG_DEV = "[-_.]?dev([-_.]?\\d+)?";
    private static final String VERSION_PATTERN_STR = "(" + VERSION_SEG_EPOCH + ")?" + VERSION_SEG_RELEASE + "(" + VERSION_SEG_PRE + ")?" + "(" + VERSION_SEG_POST + ")?" + "(" + VERSION_SEG_DEV + ")?";
    public static final Pattern VERSION_PATTERN=Pattern.compile(VERSION_PATTERN_STR);

}
