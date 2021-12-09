package com.jn.shelltools.supports.pypi.versionspecifier;

import com.jn.langx.exception.IllegalParameterException;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.LinkedCaseInsensitiveMap;
import com.jn.langx.util.collection.MapAccessor;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.struct.Holder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
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

    public static final List<String> VERSION_EXP_SPECIFIERS = Collects.immutableArrayList(
            VERSION_EXP_COMPATIBLE_RELEASE,
            VERSION_EXP_VERSION_EXCLUSION,
            VERSION_EXP_INCLUSIVE_COMPARISON_LESS_THAN,
            VERSION_EXP_INCLUSIVE_COMPARISON_GREAT_THAN,
            VERSION_EXP_EXCLUSIVE_COMPARISON_LESS_THAN,
            VERSION_EXP_EXCLUSIVE_COMPARISON_GREAT_THAN,
            VERSION_EXP_ARBITRARY_EQUALITY,
            VERSION_EXP_VERSION_MATCHING
    );

    public static final String extractPackageName(String packageName) {
        if (Strings.isBlank(packageName)) {
            return null;
        }

        // 移除 #
        int index = packageName.indexOf("#");
        if (index > -1) {
            packageName = packageName.substring(0, index);
        }
        index = packageName.indexOf(":");
        if (index > -1) {
            packageName = packageName.substring(0, index);
        }
        index = packageName.indexOf(";");
        if (index > -1) {
            packageName = packageName.substring(0, index);
        }
        if (packageName.startsWith("<") || packageName.startsWith(">") || packageName.startsWith("!") || packageName.startsWith("=")) {
            return null;
        }
        return packageName.trim();
    }

    public static final boolean isOmitSpecifier(String expression) {
        return Collects.allMatch(VERSION_EXP_SPECIFIERS, new Predicate<String>() {
            @Override
            public boolean test(String specifier) {
                return !expression.startsWith(specifier);
            }
        });
    }

    public static final boolean versionAbsent(String packageName) {
        return Pipeline.of(VERSION_EXP_SPECIFIERS).allMatch(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return !packageName.contains(s);
            }
        });
    }

    public static final boolean versionedPackageName(String packageName) {
        if (Strings.isBlank(packageName)) {
            return false;
        }
        return Pipeline.of(VERSION_EXP_SPECIFIERS).anyMatch(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                String reg = "[\\w-._]+(\\s*" + s + ").*";
                return packageName.matches(reg);
            }
        });
    }

    // https://www.python.org/dev/peps/pep-0440/#version-scheme
    // https://www.pythonheidong.com/blog/article/187997/31fe90bd992afcd027a1/
    private static final String PUBLIC_VERSION_SEG_EPOCH = "(?:(?<epoch>\\d+)!)?";
    private static final String PUBLIC_VERSION_SEG_RELEASE = "(?<release>\\d+(\\.\\d+)*)";
    private static final String PUBLIC_VERSION_SEG_PRE = "(?<pre>[-_.]?(?<preLabel>(a|alpha|b|beta|rc|c|pre|preview))([-_.]?(?<preN>\\d+))?)?";
    private static final String PUBLIC_VERSION_SEG_POST = "(?<post>[-_.]?(?<postLabel>(post|rev|r))([-_.]?(?<postN>\\d+))?)?";
    private static final String PUBLIC_VERSION_SEG_DEV = "(?<dev>[-_.]?dev([-_.]?(?<devN>\\d+))?)?";
    private static final String LOCAL_VERSION_SEG = "(?:\\+(?<local>[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*))?";
    public static final String VERSION_PATTERN_STR = PUBLIC_VERSION_SEG_EPOCH
            + PUBLIC_VERSION_SEG_RELEASE
            + PUBLIC_VERSION_SEG_PRE
            + PUBLIC_VERSION_SEG_POST
            + PUBLIC_VERSION_SEG_DEV
            + LOCAL_VERSION_SEG;
    public static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_PATTERN_STR);


    private static Map<String, Integer> preLabelMap = new LinkedCaseInsensitiveMap<Integer>();

    static {
        preLabelMap.put("alpha", 1);
        preLabelMap.put("a", 1);
        preLabelMap.put("beta", 2);
        preLabelMap.put("b", 2);
        preLabelMap.put("rc", 3);
        preLabelMap.put("c", 3);
        preLabelMap.put("pre", 4);
        preLabelMap.put("preview", 4);
    }

    public static final int comparePreLabel(String label_1, String label_2) {
        Preconditions.checkTrue(preLabelMap.containsKey(label_1));
        Preconditions.checkTrue(preLabelMap.containsKey(label_2));
        return preLabelMap.get(label_2) - preLabelMap.get(label_1);
    }

    public static final boolean validVersionExpression(String version) {
        Matcher matcher = VersionSpecifiers.VERSION_PATTERN.matcher(version);
        return matcher.matches();
    }

    public static String trimSpecifiers(String version) {
        Preconditions.checkNotNull(version);
        final Holder<String> tmp = new Holder<>(version);

        boolean continueFind = true;
        while (continueFind) {
            String spec = Collects.findFirst(VERSION_EXP_SPECIFIERS, new Predicate<String>() {
                @Override
                public boolean test(String spec) {
                    return tmp.get().startsWith(spec);
                }
            });
            if (spec == null) {
                continueFind = false;
            } else {
                tmp.set(tmp.get().substring(spec.length()));
            }
        }
        return tmp.get();
    }

    public static final MapAccessor extractVersionSegments(String version) {
        Matcher matcher = VersionSpecifiers.VERSION_PATTERN.matcher(version);
        if (matcher.matches()) {
            final Map<String, Object> map = new HashMap<String, Object>();
            Pipeline.of("epoch", "release", "pre", "preLabel", "preN", "post", "postLabel", "postN", "dev", "devN", "local")
                    .forEach(new Consumer<String>() {
                        @Override
                        public void accept(String groupName) {
                            map.put(groupName, matcher.group(groupName));
                        }
                    });
            return new MapAccessor(map);
        }
        throw new IllegalParameterException(StringTemplates.formatWithPlaceholder("version : {} is illegal", version));
    }
}
