package com.jn.shelltools.command;

import com.jn.easyjson.core.JSON;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.Strings;
import com.jn.langx.util.SystemPropertys;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.io.Charsets;
import com.jn.shelltools.core.pypi.PypiPackageManager;
import com.jn.shelltools.core.pypi.PypiPackageMetadataManager;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.util.List;

@ShellComponent
@ShellCommandGroup("pip")
public class PypiCommands {
    private static final Logger logger = LoggerFactory.getLogger(PypiCommands.class);
    @Autowired
    private PypiPackageManager pypiPackageManager;
    private PypiPackageMetadataManager pypiPackageMetadataManager;

    @ShellMethod(key = "pip show", value = "show the metadata for a python package")
    public void showMetadata(
            @ShellOption(value = "--package") String packageName) {

        PipPackageMetadata metadata = pypiPackageMetadataManager.getOfficialMetadata(packageName);
        JSON json = JSONBuilderProvider.create().prettyFormat(true).serializeNulls(true).build();
        System.out.println(json.toJson(metadata));
    }

    @ShellMethod(key = "pip download", value = "download a package (and its dependencies) to the out directory")
    public void downloadPackage(
            @ShellOption(value = "--package", help = "the package name or the requirements file path") String packageName,
            @ShellOption(value = "--with-deps", defaultValue = "true", help = "with its dependencies ?") boolean withDeps,
            @ShellOption(value = "--out", defaultValue = "__NULL__", help = "the out directory") String outDirectory) {

        if (Strings.isBlank(outDirectory)) {
            outDirectory = SystemPropertys.getUserWorkDir()+"/_tmp_";
        }
        final String out = outDirectory;
        File file = new File(packageName);
        if (!file.exists()) {
            pypiPackageManager.downloadPackage(packageName, withDeps, out);
        } else {
            Resource resource = Resources.loadFileResource(file);
            List<String> lines = Collects.emptyArrayList();
            Resources.readUsingDelimiter(resource, "\n", Charsets.UTF_8, new Consumer<String>() {
                @Override
                public void accept(String line) {
                    lines.add(line);
                }
            });
            Collects.forEach(lines, new Consumer<String>() {
                @Override
                public void accept(String line) {
                    try {
                        pypiPackageManager.downloadPackage(line, withDeps, out);
                    } catch (Throwable ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
            });
        }
    }
}
