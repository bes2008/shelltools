package com.jn.shelltools.supports.pypi;

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
import com.jn.shelltools.supports.pypi.versionspecifier.VersionSpecifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.regex.Pattern;


public class Pypis {
    private static final Logger logger = LoggerFactory.getLogger(Pypis.class);
    private static MultiValueMap<String, String> packageTypeToFileExtensions = new LinkedMultiValueMap<String, String>();

    public static final String PACKAGE_TYPE_SOURCE = "sdist";
    public static final String PACKAGE_TYPE_BINARY_WHEEL = "bdist_wheel";
    public static final String PACKAGE_TYPE_BINARY_EGG = "bdist_egg";
    public static final String PACKAGE_TYPE_BINARY_MSI = "bdist_msi";
    public static final String PACKAGE_TYPE_BINARY_EXE = "bdist_wininst";
    public static final String PACKAGE_TYPE_BINARY_RPM = "bdist_rpm";
    public static final String PACKAGE_TYPE_BINARY_HPUX_RPM = "bdist_sdux";
    public static final String PACKAGE_TYPE_BINARY_SOLARIS_RPM = "bdist_pkgtool";

    public static final String ARCHIVE_EXTENSION_ZIP = "zip";
    public static final String ARCHIVE_EXTENSION_TAR_GZ = "tar.gz";
    public static final String ARCHIVE_EXTENSION_TAR_BZ2 = "tar.bz2";
    public static final String ARCHIVE_EXTENSION_TAR_XZ = "tar.xz";
    public static final String ARCHIVE_EXTENSION_WHEEL = "whl";
    public static final String ARCHIVE_EXTENSION_EXE = "exe";
    public static final String ARCHIVE_EXTENSION_EGG = "egg";
    public static final String ARCHIVE_EXTENSION_MSI = "msi";
    public static final String ARCHIVE_EXTENSION_RPM = "rpm";

    static {
        /**
         * 源码包
         */
        packageTypeToFileExtensions.add(PACKAGE_TYPE_SOURCE, ARCHIVE_EXTENSION_ZIP);
        packageTypeToFileExtensions.add(PACKAGE_TYPE_SOURCE, ARCHIVE_EXTENSION_TAR_GZ);
        packageTypeToFileExtensions.add(PACKAGE_TYPE_SOURCE, ARCHIVE_EXTENSION_TAR_BZ2);
        packageTypeToFileExtensions.add(PACKAGE_TYPE_SOURCE, ARCHIVE_EXTENSION_TAR_XZ);
        /**
         * wheel 二进制包
         */
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_WHEEL, ARCHIVE_EXTENSION_WHEEL);

        /**
         * egg 二进制包
         */
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_EGG, ARCHIVE_EXTENSION_EGG);
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_EGG, ARCHIVE_EXTENSION_RPM);

        /**
         * windows 专用 二进制包
         */
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_MSI, ARCHIVE_EXTENSION_MSI);
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_EXE, ARCHIVE_EXTENSION_EXE);

        /**
         * rpm包
         */
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_RPM, ARCHIVE_EXTENSION_RPM);
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_HPUX_RPM, ARCHIVE_EXTENSION_RPM);
        packageTypeToFileExtensions.add(PACKAGE_TYPE_BINARY_SOLARIS_RPM, ARCHIVE_EXTENSION_RPM);

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
        Preconditions.checkTrue(Strings.startsWith(filename.replace("-", "_"), packageName.replace("-", "_"), true), "illegal file name : {} for package : {}", filename, packageName);
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
        if (Strings.isBlank(str) || Strings.isBlank(extension)) {
            System.out.println("不能识别的扩展名：" + extension);
        }
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