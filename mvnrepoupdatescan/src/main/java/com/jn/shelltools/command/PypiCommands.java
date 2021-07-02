package com.jn.shelltools.command;

import com.jn.easyjson.core.JSON;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.shelltools.core.pypi.PypiPackageManager;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@ShellCommandGroup("pip")
public class PypiCommands {

    @Autowired
    private PypiPackageManager pypiPackageManager;

    @ShellMethod(key = "pip show", value = "show the metadata for a python package")
    public void showMetadata(
            @ShellOption(value = "--package") String packageName) {

        PipPackageMetadata metadata = pypiPackageManager.getPackageMetadata(packageName);
        JSON json = JSONBuilderProvider.create().prettyFormat(true).serializeNulls(true).build();
        System.out.println(json.toJson(metadata));
    }

    @ShellMethod(key = "pip download", value = "download a package (and its dependencies) to the out directory")
    public void downloadPackage(
            @ShellOption(value = "--package", help = "the package name") String packageName,
            @ShellOption(value = "--with-deps", defaultValue = "true", help = "with its dependencies ?") boolean withDeps,
            @ShellOption(value = "--out", defaultValue = "__NULL__", help = "the out directory") String outDirectory) {
        pypiPackageManager.downloadPackage(packageName, withDeps, outDirectory);
    }
}
