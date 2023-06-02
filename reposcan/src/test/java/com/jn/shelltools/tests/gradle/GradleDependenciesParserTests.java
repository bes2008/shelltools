package com.jn.shelltools.tests.gradle;

import com.jn.easyjson.core.util.JSONs;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import com.jn.shelltools.supports.gradle.GradleProjectDependenciesParser;
import org.junit.Test;

import java.util.List;

public class GradleDependenciesParserTests {
    @Test
    public void test() {
        Resource resource = Resources.loadClassPathResource("/gradle/spring-boot-2.7.12-build.gradle");
        List<DependencyModel> gavs = new GradleProjectDependenciesParser().parse(resource);
        System.out.println(JSONs.toJson(gavs, true));
    }
}
