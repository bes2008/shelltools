package com.jn.shelltools.tests.maven;

import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.text.xml.Xmls;
import com.jn.langx.text.xml.errorhandler.IgnoreErrorHandler;
import com.jn.shelltools.supports.maven.MavenPomParser;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import org.junit.Test;
import org.w3c.dom.Document;

import java.io.InputStream;

public class PomParseTests {
    @Test
    public void test() throws Throwable {
        Resource resource = Resources.loadClassPathResource("/pom/tests/bsh-2.0b4.pom");
        InputStream stream = resource.getInputStream();
        Document document = Xmls.getXmlDoc(null, new IgnoreErrorHandler(),stream);
        MavenPackageArtifact artifact = new MavenPomParser(null).parse(document);
        System.out.println(artifact);
    }

}
