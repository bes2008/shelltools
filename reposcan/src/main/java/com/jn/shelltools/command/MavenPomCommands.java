package com.jn.shelltools.command;

import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Dates;
import com.jn.langx.util.io.file.Files;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.dependencies.MavenDependenciesTreeParser;
import com.jn.shelltools.supports.maven.dependencies.MavenDependenciesTreeToPomTransformer;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;

@ShellComponent
public class MavenPomCommands {

    private Configuration freemarkerConfig;

    private MavenDependenciesTreeParser dependenciesTreeStyleDependenciesParser;

    @ShellMethod(key = "pom-gen", value = "generate pom.xml from dependencies tree")
    public String genPom(
            @ShellOption(value = "--deps", help = "the file of dependencies") String deps,
            @ShellOption(value = "--out", defaultValue = ".", help = "the out file path") String outdir,
            @ShellOption(defaultValue = "com.jn.sheeltools") String groupId,
            @ShellOption(defaultValue = "reposcan") String artifactId,
            @ShellOption(defaultValue = "1.0.0") String version
    ) throws IOException {

        Resource resource = Resources.loadResource(deps);
        if (!resource.exists()) {
            throw new IllegalArgumentException(StringTemplates.formatWithPlaceholder("invalid dependencies resource: {}", deps));
        }

        File outDir = new File(outdir);
        if (!outDir.exists()) {
            Files.makeDirs(outDir);
        }

        PackageGAV packageGav = new PackageGAV(groupId, artifactId, version);
        String xml = MavenDependenciesTreeToPomTransformer.transform(dependenciesTreeStyleDependenciesParser, resource, packageGav, freemarkerConfig);

        String filename = StringTemplates.formatWithPlaceholder("{}__{}__{}__{}__pom.xml", groupId, artifactId, version, Dates.now().getTime());
        File pomFile = new File(outdir, filename);
        if(!Files.exists(pomFile)){
            Files.makeFile(pomFile);
            Files.write(xml, pomFile);
        }
        return xml;
    }

    @Autowired
    public void setFreemarkerConfig(@Qualifier("templateConfig") Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Autowired
    public void setDependenciesTreeStyleDependenciesParser(MavenDependenciesTreeParser dependenciesTreeStyleDependenciesParser) {
        this.dependenciesTreeStyleDependenciesParser = dependenciesTreeStyleDependenciesParser;
    }
}
