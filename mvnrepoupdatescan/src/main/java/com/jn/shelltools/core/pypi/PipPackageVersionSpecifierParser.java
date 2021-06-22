package com.jn.shelltools.core.pypi;

import com.jn.langx.Parser;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.boundary.CommonExpressionBoundary;
import com.jn.langx.util.struct.pair.NameValuePair;

/**
 * 参考官方规范：
 * https://www.python.org/dev/peps/pep-0345/#version-specifiers
 */
public class PipPackageVersionSpecifierParser implements Parser<String, NameValuePair<CommonExpressionBoundary>> {
    @Override
    public NameValuePair<CommonExpressionBoundary> parse(String versionedPackageName) {
        Preconditions.checkNotEmpty(versionedPackageName);

        versionedPackageName  = Strings.trim(versionedPackageName);

        if(versionedPackageName.startsWith("-r")){
            versionedPackageName=Strings.trim(versionedPackageName.substring(2));
        }
        return null;
    }

}
