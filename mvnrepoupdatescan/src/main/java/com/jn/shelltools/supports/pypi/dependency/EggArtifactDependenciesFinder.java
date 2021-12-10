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

public class EggArtifactDependenciesFinder extends AbstractArtifactDependenciesFinder {
    @Override
    protected String getExpanderFormat(PypiArtifact artifact) {
        return "zip";
    }

    @Override
    public List<String> supportedExtensions() {
        return Collects.newArrayList(Pypis.ARCHIVE_EXTENSION_EGG);
    }

    @Override
    protected List<String> parseDependencies(PypiArtifact pypiArtifact, String tmpExpandDir) {
        // 在 <EGG-INFO> 目录下找到 PKG-INFO 文件
        FileFilter metadataFileFilter = FileFilters.allFileFilter(
                new IsFileFilter(),
                new FilenameEqualsFilter("PKG-INFO"),
                new ParentFilenameSuffixFilter(Collects.newArrayList("EGG-INFO"), false),
                new ReadableFileFilter()
        );

        List<File> files = Files.find(new File(tmpExpandDir), 2,
                FileFilters.anyFileFilter(
                        metadataFileFilter,
                        FileFilters.allFileFilter(
                                new IsDirectoryFileFilter(),
                                new FilenameEqualsFilter("EGG-INFO")
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
