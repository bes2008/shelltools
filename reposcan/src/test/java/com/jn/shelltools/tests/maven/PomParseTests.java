package com.jn.shelltools.tests.maven;

import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.shelltools.supports.maven.MavenLocalRepositoryUpdatedScanner;
import com.jn.shelltools.supports.maven.MavenPomParser;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.InputStream;

public class PomParseTests {
    @Test
    public void test() throws Throwable {
        Resource resource = Resources.loadClassPathResource("/pom/tests/bsh-2.0b4.pom");
        InputStream stream = resource.getInputStream();
        Document document = MavenLocalRepositoryUpdatedScanner.getXmlDoc(null, stream, new IgnoreErrorHandler());
        MavenPackageArtifact artifact = new MavenPomParser(null).parse(document);
        System.out.println(artifact);
    }


    private static class IgnoreErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException exception) throws SAXException {

        }

        @Override
        public void error(SAXParseException exception) throws SAXException {

        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {

        }
    }

}
