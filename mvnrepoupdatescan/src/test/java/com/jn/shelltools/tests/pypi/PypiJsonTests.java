package com.jn.shelltools.tests.pypi;

import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.shelltools.supports.pypi.packagemetadata.PipPackageMetadata;
import org.junit.Test;

import java.io.InputStreamReader;

public class PypiJsonTests {
    @Test
    public void test() throws Throwable {
        Resource resource = Resources.loadResource("classpath:/pypi/tests/pip.json");
        InputStreamReader inputStream = new InputStreamReader(resource.getInputStream());
        PipPackageMetadata summary = JSONBuilderProvider.simplest().fromJson(inputStream, PipPackageMetadata.class);
        System.out.println(JSONBuilderProvider.create().prettyFormat(true).build().toJson(summary));

    }
}
