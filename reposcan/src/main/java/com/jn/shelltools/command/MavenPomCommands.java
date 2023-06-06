package com.jn.shelltools.command;

import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.io.file.Files;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.dependencies.MavenDependenciesTreeStyleDependenciesParser;
import com.jn.shelltools.supports.maven.dependencies.MavenDependenciesTreeStyleToPomTransformer;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;

@ShellComponent
public class MavenPomCommands {

    private Configuration freemarkerConfig;

    private MavenDependenciesTreeStyleDependenciesParser dependenciesTreeStyleDependenciesParser;

    @ShellMethod(key = "pom-gen", value = "generate pom.xml from dependencies tree")
    public String genPom(
            @ShellOption(value = "--deps", help = "the file of dependencies") String deps,
            @ShellOption(value = "--out", defaultValue = ".", help = "the out file path") String outdir,
            @ShellOption(defaultValue = "com.jn.sheeltools") String groupId,
            @ShellOption(defaultValue = "reposcan") String artifactId,
            @ShellOption(defaultValue = "1.0.0") String version
    ) {

        Resource resource = Resources.loadResource(deps);
        if (!resource.exists()) {
            throw new IllegalArgumentException(StringTemplates.formatWithPlaceholder("invalid dependencies resource: {}", deps));
        }

        File outDir = new File(outdir);
        if (!outDir.exists()) {
            Files.makeDirs(outDir);
        }

        PackageGAV packageGav = new PackageGAV(groupId, artifactId, version);
        String xml = MavenDependenciesTreeStyleToPomTransformer.transform(dependenciesTreeStyleDependenciesParser, resource, packageGav, freemarkerConfig);
        return xml;
    }

    @Autowired
    public void setFreemarkerConfig(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Autowired
    public void setDependenciesTreeStyleDependenciesParser(MavenDependenciesTreeStyleDependenciesParser dependenciesTreeStyleDependenciesParser) {
        this.dependenciesTreeStyleDependenciesParser = dependenciesTreeStyleDependenciesParser;
    }
}
