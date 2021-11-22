package com.jn.shelltools.core.pypi;

import com.jn.langx.annotation.NonNull;
import com.jn.langx.annotation.Nullable;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.collection.multivalue.LinkedMultiValueMap;
import com.jn.langx.util.collection.multivalue.MultiValueMap;
import com.jn.langx.util.function.Functions;
import com.jn.langx.util.function.Predicate;
import com.jn.shelltools.core.pypi.versionspecifier.VersionSpecifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.regex.Pattern;


public class Pypis {
    private static final Logger logger = LoggerFactory.getLogger(Pypis.class);
    private static MultiValueMap<String, String> packageTypeToFileExtensions = new LinkedMultiValueMap<String, String>();

    static {
        /**
         * 源码包
         */
        packageTypeToFileExtensions.add("sdist", "zip");
        packageTypeToFileExtensions.add("sdist", "tar.gz");

        /**
         * wheel 二进制包
         */
        packageTypeToFileExtensions.add("bdist_wheel", "whl");

        /**
         * egg 二进制包
         */
        packageTypeToFileExtensions.add("bdist_egg", "egg");

        /**
         * windows 专用 二进制包
         */
        packageTypeToFileExtensions.add("bdist_msi", "msi");
    }

    public static Collection<String> getFileExtensions(String packageType) {
        return packageTypeToFileExtensions.get(packageType);
    }

    public static Collection<String> getAllFileExtensions() {
        return Pipeline.of(packageTypeToFileExtensions.values()).flatMap(Functions.<String>noopFunction()).asList();
    }

    private static Pattern PACKAGE_FILE_NAME_PATTERN = Pattern.compile("(?<packageName>[a-zA-Z]\\w*)[-_.](?<version>" + VersionSpecifiers.VERSION_PATTERN_STR + ")");

    public static PypiArtifact gaussFileArtifact(@NonNull String filename, @NonNull String packageName, @NonNull String version, @Nullable String packageType) {
        Preconditions.checkNotNull(filename);
        Preconditions.checkNotNull(packageName);
        Preconditions.checkNotNull(version);
        Preconditions.checkTrue(Strings.startsWith(filename, packageName.replace("-","_"), true), "illegal file name : {} for package : {}", filename, packageName);
        // 移除package name
        String str = filename.substring(packageName.length());
        if (Strings.startsWith(str, ".") || Strings.startsWith(str, "-") || Strings.startsWith(str, "_")) {
            str = str.substring(1);
        }
        // 移除 version
        if (Strings.startsWith(str, version, true)) {
            str = str.substring(version.length());
        } else {
            throw new IllegalArgumentException(StringTemplates.formatWithPlaceholder("illegal file name: {}, for package version: {}", filename, version));
        }

        // 移除 扩展名
        Collection<String> extensions = null;
        if (Strings.isNotEmpty(packageType)) {
            extensions = getFileExtensions(packageType);
        }
        if (Objs.isEmpty(extensions)) {
            extensions = getAllFileExtensions();
        }
        final String _str = str;
        String extension = Collects.findFirst(extensions, new Predicate<String>() {
            @Override
            public boolean test(String extension) {
                return Strings.endsWith(_str, extension, true);
            }
        });
        str = str.substring(0, str.length() - extension.length());
        if (Strings.endsWith(str, ".")) {
            str = str.substring(0, str.length() - 1);
        }
        if (Strings.startsWith(str, ".") || Strings.startsWith(str, "-") || Strings.startsWith(str, "_")) {
            str = str.substring(1);
        }

        String classifier = str;
        PypiArtifact artifact = new PypiArtifact();
        artifact.setGroupId(packageName);
        artifact.setArtifactId(packageName);
        artifact.setVersion(version);
        artifact.setExtension(extension);
        if (Strings.isNotEmpty(classifier)) {
            artifact.setClassifier(classifier);
        }

        return artifact;

    }

}
