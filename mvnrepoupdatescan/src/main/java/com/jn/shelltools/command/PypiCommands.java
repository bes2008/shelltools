package com.jn.shelltools.command;

import com.jn.easyjson.core.JSON;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.shelltools.core.pypi.PypiService;
import com.jn.shelltools.core.pypi.packagemetadata.PipPackageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@ShellCommandGroup
public class PypiCommands {

    @Autowired
    private PypiService pypiService;

    @ShellMethod(key="pip-show" , value = "show the metadata for a python package")
    public void showMetadata(
            @ShellOption(value = "--package") String packageName) {
        PipPackageMetadata metadata = pypiService.packageMetadata(packageName);
        JSON json = JSONBuilderProvider.create().prettyFormat(true).serializeNulls(true).build();
        System.out.println(json.toJson(metadata));
    }
}
