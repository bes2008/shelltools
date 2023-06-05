package com.jn.shelltools.tests.maven;

import com.jn.easyjson.core.util.JSONs;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.regexp.Regexps;
import com.jn.shelltools.config.FreemarkerConfig;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.dependencies.MavenDependenciesTreeStyleDependenciesParser;
import com.jn.shelltools.supports.maven.dependencies.MavenDependenciesTreeStyleToPomTransformer;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import freemarker.template.Configuration;
import org.junit.Test;

import java.util.List;

public class TreeStyleDependenciesParserTests {
    private Resource resource = Resources.loadClassPathResource("/maven/dependencies/gradle-dependencies-example.txt");
    PackageGAV packageGav = new PackageGAV("org.springframework.boot", "boot-all", "2.7.12");

    @Test
    public void regexpTest(){
        System.out.println(Regexps.match(MavenDependenciesTreeStyleDependenciesParser.dependencyExpr, "debugCompileClasspath - Resolved configuration for compilation for variant: debug"));
        System.out.println(Regexps.match(MavenDependenciesTreeStyleDependenciesParser.dependencyExpr, "org.jetbrains.kotlin:kotlin-stdlib-jre7:1.2.31"));
        System.out.println(Regexps.match(MavenDependenciesTreeStyleDependenciesParser.dependencyExpr, "org.jetbrains.kotlin:kotlin-stdlib:1.2.31"));
        System.out.println(Regexps.match(MavenDependenciesTreeStyleDependenciesParser.dependencyExpr,"com.android.support:support-annotations:27.1.1"));
        System.out.println(Regexps.match(MavenDependenciesTreeStyleDependenciesParser.dependencyExpr,"\\\\--- com.android.support.constraint:constraint-layout:1.0.2"));

    }

    @Test
    public void testParse() {
        List<DependencyModel> gavs = new MavenDependenciesTreeStyleDependenciesParser().parse(resource);
        System.out.println(JSONs.toJson(gavs, true));
    }

    @Test
    public void testTransform() throws Throwable {
        Configuration cfg = new FreemarkerConfig().templateConfig();
        String xml = MavenDependenciesTreeStyleToPomTransformer.transform(resource, packageGav, cfg);
        System.out.println(xml);
    }
}
