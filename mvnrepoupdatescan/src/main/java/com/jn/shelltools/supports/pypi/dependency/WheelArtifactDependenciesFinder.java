package com.jn.shelltools.supports.pypi.dependency;

import com.jn.langx.util.Objs;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Predicate2;
import com.jn.langx.util.io.file.FileFilter;
import com.jn.langx.util.io.file.FileFilters;
import com.jn.langx.util.io.file.Files;
import com.jn.langx.util.io.file.filter.*;
import com.jn.shelltools.supports.pypi.PypiArtifact;
import com.jn.shelltools.supports.pypi.Pypis;
import com.jn.shelltools.supports.pypi.dependency.parser.PkginfoParser;

import java.io.File;
import java.util.List;

public class WheelArtifactDependenciesFinder extends AbstractArtifactDependenciesFinder {

    @Override
    protected boolean isArchive(PypiArtifact pypiArtifact) {
        return Pypis.ARCHIVE_EXTENSION_WHEEL.equalsIgnoreCase(pypiArtifact.getExtension());
    }

    @Override
    public List<String> supportedExtensions() {
        return Collects.asList(Pypis.getFileExtensions(Pypis.PACKAGE_TYPE_BINARY_WHEEL));
    }

    @Override
    protected List<String> parseDependencies(PypiArtifact pypiArtifact, String tmpExpandDir) {
        // 在 <packageName>-<version>.dist-info 目录下找到 METADATA 文件
        FileFilter metadataFileFilter = FileFilters.allFileFilter(
                new IsFileFilter(),
                new FilenameEqualsFilter("METADATA"),
                new ParentFilenameSuffixFilter(Collects.newArrayList("dist-info"), false),
                new ReadableFileFilter()

        );

        List<File> files = Files.find(new File(tmpExpandDir), 2,
                FileFilters.anyFileFilter(
                        metadataFileFilter,
                        FileFilters.allFileFilter(
                                new IsDirectoryFileFilter(),
                                new FilenameSuffixFilter("dist-info")
                        )
                ),
                metadataFileFilter, new Predicate2<List<File>, File>() {
                    @Override
                    public boolean test(List<File> files, File file) {
                        return !files.isEmpty();
                    }
                });

        if (Objs.isEmpty(files)) {
            return null;
        }
        File metadataFile = files.get(0);
        return new PkginfoParser().parse(metadataFile);
    }
}
