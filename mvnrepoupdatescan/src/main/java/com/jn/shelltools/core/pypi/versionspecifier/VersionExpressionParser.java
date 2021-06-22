package com.jn.shelltools.core.pypi.versionspecifier;

import com.jn.langx.util.Strings;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.boundary.ExpressionParser;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.function.Functions;
import com.jn.langx.util.function.Predicate;

import java.util.List;

public class VersionExpressionParser implements ExpressionParser {

    @Override
    public CommonExpressionBoundary parse(String expression) {
        CommonExpressionBoundary boundary = new CommonExpressionBoundary();
        boundary.setExpression(expression);

        // 根据 , 进行分割
        List<String> segments = Collects.asList(Strings.split(expression, ","));
        Pipeline.of(segments)
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
                    public List<String> apply(String segment) {
                        if (segment.startsWith(VersionSpecifiers.VERSION_EXP_COMPATIBLE_RELEASE)) {
                            // 移除 ~=
                            segment = segment.substring(VersionSpecifiers.VERSION_EXP_COMPATIBLE_RELEASE.length());

                            List<String> segs = Pipeline.of(Strings.split(segment, ".")).filter(Functions.notEmptyPredicate()).asList();
                            // 从后面往前开始查找，找到是整数的seg
                            int i = segs.size() - 1;
                            for (; i >= 0; i--) {
                                try {
                                    Integer.parseInt(segs.get(i));
                                    break;
                                } catch (Throwable ex) {
                                    // ignore it
                                }
                            }
                            StringBuilder versionMatching = new StringBuilder("== ");
                            for (int j = 0; j <= i; j++) {
                                if (j != 0) {
                                    versionMatching.append(".");
                                }
                                versionMatching.append(segs.get(j));
                            }

                            return Collects.newArrayList(">= " + Strings.join(".", segs), versionMatching.toString());
                        } else {
                            return Collects.newArrayList(segment);
                        }
                    }
                }).flatMap(new Function<String, Predicate<String>>() {
            @Override
            public Predicate<String> apply(String segment) {
                if (segment.startsWith(VersionSpecifiers.VERSION_EXP_VERSION_EXCLUSION)) {
                    String seg = Strings.trim(segment.substring(VersionSpecifiers.VERSION_EXP_VERSION_EXCLUSION.length()));

                    return new Predicate<String>(){
                        @Override
                        public boolean test(String s) {
                            return false;
                        }
                    };
                }
                return null;
            }
        }).forEach(new Consumer<Predicate<String>>() {
            @Override
            public void accept(Predicate<String> predicate) {
                boundary.addPredicate(predicate);
            }
        });


        return boundary;
    }
}
