package com.jn.shelltools.tests.pypi;

import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.Strings;
import com.jn.shelltools.supports.pypi.dependency.parser.TomlDependenciesParser;
import org.junit.Test;

import java.io.InputStream;

public class TomlConfigParserTests {
    @Test
    public void test() throws Throwable {
        Resource resource = Resources.loadResource("classpath:/pypi/tests/py-1.11.0.pyproject.toml");

        InputStream inputStream = resource.getInputStream();
        System.out.printf(Strings.join("\n", new TomlDependenciesParser().parse(inputStream)));

    }
}
