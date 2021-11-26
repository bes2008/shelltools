package com.jn.shelltools.supports.pypi.versionspecifier;

import com.jn.langx.util.Strings;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.boundary.ExpressionParser;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.MapAccessor;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.function.Functions;
import com.jn.langx.util.function.Predicate;
import com.jn.shelltools.supports.pypi.versionspecifier.predicates.ComparisonPredicate;
import com.jn.shelltools.supports.pypi.versionspecifier.predicates.EqualsPredicate;
import com.jn.shelltools.supports.pypi.versionspecifier.predicates.VersionMatchingPredicate;

import java.util.List;

public class VersionExpressionParser implements ExpressionParser {

    @Override
    public CommonExpressionBoundary parse(String expression) {
        CommonExpressionBoundary boundary = new CommonExpressionBoundary();
        boundary.setExpression(expression);

        // 根据 , 进行分割
        List<String> expressions = Collects.asList(Strings.split(expression, ","));
        Pipeline.of(expressions)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        s = Strings.trim(s);
                        // 如果省略了前缀，给加上前缀
                        if (VersionSpecifiers.isOmitSpecifier(s)) {
                            s = "== " + s;
                        }
                        // 如果只有前缀，则置空
                        final String _s = s;
                        String specifier = Collects.findFirst(VersionSpecifiers.VERSION_EXP_SPECIFIERS, new Predicate<String>() {
                            @Override
                            public boolean test(String specifier) {
                                return _s.startsWith(specifier);
                            }
                        });
                        if (specifier == null) {
                            return "";
                        }
                        if (Strings.isBlank(s.substring(specifier.length()))) {
                            return "";
                        }
                        return s;
                    }
                })
                .filter(Functions.notEmptyPredicate())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String expression) {
                        if (expression.startsWith(VersionSpecifiers.VERSION_EXP_COMPATIBLE_RELEASE)) {
                            // 移除 ~=
                            expression = expression.substring(VersionSpecifiers.VERSION_EXP_COMPATIBLE_RELEASE.length());


                            MapAccessor versionSegments = VersionSpecifiers.extractVersionSegments(expression);
                            // 拼接 == version.*
                            StringBuilder versionMatching = new StringBuilder("== ");
                            if (versionSegments.has("epoch")) {
                                versionMatching.append(versionSegments.getString("epoch") + "!");
                            }
                            String release = versionSegments.getString("release");
                            String[] segments = Strings.split(release, ".");
                            if (segments.length > 1) {
                                for (int j = 0; j <= segments.length - 1; j++) {
                                    if (j != 0) {
                                        versionMatching.append(".");
                                    }
                                    versionMatching.append(segments[j]);
                                }
                                versionMatching.append(".*");
                            }

                            return segments.length > 1 ? Collects.newArrayList(">= " + expression, versionMatching.toString()) : Collects.newArrayList(">= " + expression);
                        } else {
                            return Collects.newArrayList(expression);
                        }
                    }
                }).flatMap(new Function<String, VersionPredicate>() {
            @Override
            public VersionPredicate apply(String expression) {
                if (expression.startsWith(VersionSpecifiers.VERSION_EXP_ARBITRARY_EQUALITY)) {
                    String seg = Strings.trim(expression.substring(VersionSpecifiers.VERSION_EXP_ARBITRARY_EQUALITY.length()));
                    return new EqualsPredicate(seg);
                } else if (expression.startsWith(VersionSpecifiers.VERSION_EXP_VERSION_MATCHING) || expression.startsWith(VersionSpecifiers.VERSION_EXP_VERSION_EXCLUSION)) {
                    boolean isNon = expression.startsWith(VersionSpecifiers.VERSION_EXP_VERSION_EXCLUSION);
                    String seg = Strings.trim(expression.substring(2));
                    boolean isPrefix = Strings.endsWith(seg, ".*");
                    if (isPrefix) {
                        seg = seg.substring(0, seg.length() - ".*".length());
                    }
                    return new VersionMatchingPredicate(seg, isNon, isPrefix);
                } else if (expression.startsWith(VersionSpecifiers.VERSION_EXP_INCLUSIVE_COMPARISON_GREAT_THAN)
                        || expression.startsWith(VersionSpecifiers.VERSION_EXP_INCLUSIVE_COMPARISON_LESS_THAN)
                        || expression.startsWith(VersionSpecifiers.VERSION_EXP_EXCLUSIVE_COMPARISON_LESS_THAN)
                        || expression.startsWith(VersionSpecifiers.VERSION_EXP_EXCLUSIVE_COMPARISON_GREAT_THAN)) {

                    boolean lessThan = expression.startsWith(VersionSpecifiers.VERSION_EXP_INCLUSIVE_COMPARISON_LESS_THAN) || expression.startsWith(VersionSpecifiers.VERSION_EXP_EXCLUSIVE_COMPARISON_LESS_THAN);
                    String seg = null;
                    boolean inclusive = false;
                    if (expression.charAt(1) == '=') {
                        inclusive = true;
                        seg = Strings.trim(expression.substring(2));
                    } else {
                        seg = Strings.trim(expression.substring(1));
                    }
                    return new ComparisonPredicate(seg, inclusive, lessThan);
                }
                return null;
            }
        }).clearNulls()
                .forEach(new Consumer<VersionPredicate>() {
                    @Override
                    public void accept(VersionPredicate predicate) {
                        boundary.addPredicate(predicate);
                    }
                });

        return boundary;
    }
}
