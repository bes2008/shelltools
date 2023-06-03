package com.jn.shelltools.tests.gradle;

import com.jn.easyjson.core.util.JSONs;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.shelltools.config.FreemarkerConfig;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.gradle.GradleProjectDependenciesParser;
import com.jn.shelltools.supports.gradle.GradleToMavenPomTransformer;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import freemarker.template.Configuration;
import org.junit.Test;

import java.util.List;

public class GradleDependenciesParserTests {
    private Resource resource = Resources.loadClassPathResource("/gradle/spring-boot-2.7.12-build.gradle");
    PackageGAV packageGav = new PackageGAV("org.springframework.boot", "boot-all", "2.7.12");

    @Test
    public void testParse() {
        List<DependencyModel> gavs = new GradleProjectDependenciesParser().parse(resource);
        System.out.println(JSONs.toJson(gavs, true));
    }

    @Test
    public void testTransform() throws Throwable {
        Configuration cfg = new FreemarkerConfig().templateConfig();
        String xml = GradleToMavenPomTransformer.transform(resource, packageGav, cfg);
        System.out.println(xml);
    }
}
