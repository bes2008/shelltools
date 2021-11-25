package com.jn.shelltools.core.pypi.dependency;

import com.jn.agileway.zip.archive.AutowiredArchiveSuiteFactory;
import com.jn.agileway.zip.archive.Expander;
import com.jn.langx.io.resource.FileResource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Predicate2;
import com.jn.langx.util.io.file.FileFilter;
import com.jn.langx.util.io.file.FileFilters;
import com.jn.langx.util.io.file.Files;
import com.jn.langx.util.io.file.filter.*;
import com.jn.langx.util.logging.Loggers;
import com.jn.shelltools.core.pypi.PypiArtifact;
import com.jn.shelltools.core.pypi.Pypis;
import com.jn.shelltools.core.pypi.dependency.parser.PyprojectParser;
import org.apache.commons.vfs2.FileObject;

import java.io.File;
import java.net.URL;
import java.util.List;

public class SourceArtifactDependenciesFinder extends AbstractArtifactDependenciesFinder {


    @Override
    protected String expandArtifact(PypiArtifact pypiArtifact, FileObject tmpFileObject) {
        try {
            File localTempFile = Files.toFile(new URL(tmpFileObject.getName().getURI()));
            FileResource resource = Resources.loadFileResource(localTempFile);

            Expander expander = AutowiredArchiveSuiteFactory.getInstance().get(pypiArtifact.getExtension(), resource.getInputStream());
            expander.setOverwriteExistsFiles(true);

            File tmpExpandDir = new File(localTempFile.getParentFile(), pypiArtifact.getArtifactId());
            expander.expandTo(tmpExpandDir);
            expander.close();
            return tmpExpandDir.getAbsolutePath();
        } catch (Throwable ex) {
            Loggers.getLogger(WheelArtifactDependenciesFinder.class).error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<String> supportedExtensions() {
        return Collects.asList(Pypis.getFileExtensions(Pypis.PACKAGE_TYPE_SOURCE));
    }

    @Override
    protected List<String> parseDependencies(PypiArtifact pypiArtifact, String tmpExpandDir) {
        List<String> dependencies = Collects.emptyArrayList();

        // 从 pyproject.toml 中找
        List<String> deps1 = parsePyproject(pypiArtifact, tmpExpandDir);
        Collects.addAll(dependencies, deps1);

        // 从 setup.cfg 中找

        // 从 setup.py 中找


        return dependencies;
    }

    private List<String> parsePyproject(PypiArtifact pypiArtifact, String tmpExpandDir) {
        // 找到 pyproject.toml 文件并进行解析

        FileFilter projectFileFilter = FileFilters.allFileFilter(
                new IsFileFilter(),
                new FilenameEqualsFilter("pyproject.toml"),
                new ReadableFileFilter()
        );

        List<File> files = Files.find(new File(tmpExpandDir), 2, null,
                projectFileFilter, new Predicate2<List<File>, File>() {
                    @Override
                    public boolean test(List<File> files, File file) {
                        return !files.isEmpty();
                    }
                });

        File pyprojectFile = files.isEmpty() ? null : files.get(0);
        return new PyprojectParser().parse(pyprojectFile);
    }
}
