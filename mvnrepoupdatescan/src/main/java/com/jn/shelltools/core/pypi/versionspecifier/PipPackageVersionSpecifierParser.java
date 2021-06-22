package com.jn.shelltools.core.pypi.versionspecifier;

import com.jn.langx.Parser;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.comparator.IntegerComparator;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.struct.pair.NameValuePair;

/**
 * 参考官方规范：
 * https://www.python.org/dev/peps/pep-0345/#version-specifiers
 * https://www.python.org/dev/peps/pep-0440/
 */
public class PipPackageVersionSpecifierParser implements Parser<String, NameValuePair<CommonExpressionBoundary>> {
    @Override
    public NameValuePair<CommonExpressionBoundary> parse(String versionedPackageName) {
        Preconditions.checkNotEmpty(versionedPackageName);

        versionedPackageName = Strings.trim(versionedPackageName);

        if (versionedPackageName.startsWith("-r")) {
            versionedPackageName = Strings.trim(versionedPackageName.substring(2));
        }

        // 在 Requires-Dist 信息中，可能会存在多个部分：
        // 例如：Requires-Dist: pywin32 (>1.0); sys.platform == 'win32'
        // 所以第一步，要先排除掉 ; 之后的内容

        int semicolonIndex = versionedPackageName.indexOf(";");
        if (semicolonIndex != -1) {
            versionedPackageName = Strings.trim(versionedPackageName.substring(0, semicolonIndex));
        }

        // 去掉 ( 和 )
        versionedPackageName = Strings.trim(versionedPackageName.replace("(", "").replace(")", ""));

        // 找到开始位置
        final String _packageName = versionedPackageName;
        int index = Pipeline.of(VersionSpecifiers.VERSION_EXP_SPECIFIERS)
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String specifier) {
                        return _packageName.indexOf(specifier);
                    }
                })
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer index) {
                        return index > 0;
                    }
                }).min(new IntegerComparator());

        String packageName = versionedPackageName.substring(0, index);
        String versionExpression = versionedPackageName.substring(index);

        return null;


    }

}
