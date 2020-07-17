package com.jn.shelltools.core.maven;

import com.jn.langx.Parser;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.text.xml.Namespaces;
import com.jn.langx.text.xml.XmlAccessor;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.function.Supplier;
import com.jn.shelltools.core.maven.model.GAV;
import com.jn.shelltools.core.maven.model.MavenArtifact;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

public class MavenArtifactPomParser implements Parser<Document, MavenArtifact> {
    private static final Map<String, String> gavXPathMap = new HashMap<>();
    private static final Map<String, String> gavXPathMap_CustomNamespace = new HashMap<>();

    static {
        gavXPathMap.put("groupIdXPath", "/project/groupId");
        gavXPathMap.put("artifactIdXPath", "/project/artifactId");
        gavXPathMap.put("nameXPath", "/project/name");
        gavXPathMap.put("versionXPath", "/project/version");
        gavXPathMap.put("parentGroupIdXPath", "/project/parent/groupId");
        gavXPathMap.put("parentVersionXPath", "/project/parent/version");
    }

    static {
        gavXPathMap_CustomNamespace.put("groupIdXPath", "/x:project/x:groupId");
        gavXPathMap_CustomNamespace.put("artifactIdXPath", "/x:project/x:artifactId");
        gavXPathMap_CustomNamespace.put("nameXPath", "/x:project/x:name");
        gavXPathMap_CustomNamespace.put("versionXPath", "/x:project/x:version");
        gavXPathMap_CustomNamespace.put("parentGroupIdXPath", "/x:project/x:parent/x:groupId");
        gavXPathMap_CustomNamespace.put("parentVersionXPath", "/x:project/x:parent/x:version");
    }

    private String pomPath;

    public MavenArtifactPomParser(String pomPath) {
        this.pomPath = pomPath;
    }

    public static String getGavXpath(String key, boolean usingCustomNamespace) {
        return usingCustomNamespace ? gavXPathMap_CustomNamespace.get(key) : gavXPathMap.get(key);
    }

    @Override
    public MavenArtifact parse(Document pom) {
        MavenArtifact mavenArtifact = new MavenArtifact();
        GAV gav = parseGav(pom);
        mavenArtifact.setGav(gav);
        return mavenArtifact;
    }

    private GAV parseGav(Document doc) {
        boolean usingCustomNamespace = Namespaces.hasCustomNamespace(doc);
        String groupIdXPath = getGavXpath("groupIdXPath", usingCustomNamespace);
        String artifactIdXPath = getGavXpath("artifactIdXPath", usingCustomNamespace);
        String nameXPath = getGavXpath("nameXPath", usingCustomNamespace);
        String versionXPath = getGavXpath("versionXPath", usingCustomNamespace);
        String parentGroupIdXPath = getGavXpath("parentGroupIdXPath", usingCustomNamespace);
        String parentVersionXPath = getGavXpath("parentVersionXPath", usingCustomNamespace);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XmlAccessor xmlAccessor = usingCustomNamespace ? new XmlAccessor("x") : new XmlAccessor();
        try {
            // groupId
            Element groupIdElement = xmlAccessor.getElement(doc, xPathFactory, groupIdXPath);
            if (groupIdElement == null) {
                groupIdElement = xmlAccessor.getElement(doc, xPathFactory, parentGroupIdXPath);
            }
            String groupId = null;
            Preconditions.checkNotNull(groupIdElement, new Supplier<Object[], String>() {
                @Override
                public String get(Object[] input) {
                    return StringTemplates.formatWithPlaceholder("can't find the <groupId/> in the doc {}", input);
                }
            }, this.pomPath);
            groupId = groupIdElement.getTextContent().trim();

            // version
            Element versionElement = xmlAccessor.getElement(doc, xPathFactory, versionXPath);
            if (versionElement == null) {
                versionElement = xmlAccessor.getElement(doc, xPathFactory, parentVersionXPath);
            }

            String version = null;
            Preconditions.checkNotNull(versionElement, new Supplier<Object[], String>() {
                @Override
                public String get(Object[] input) {
                    return StringTemplates.formatWithPlaceholder("can't find the <version/> in the doc {}", input);
                }
            }, this.pomPath);
            version = versionElement.getTextContent().trim();

            // artifact
            Element artifactElement = xmlAccessor.getElement(doc, xPathFactory, artifactIdXPath);
            if (artifactElement == null) {
                artifactElement = xmlAccessor.getElement(doc, xPathFactory, nameXPath);
            }
            Preconditions.checkNotNull(artifactElement, new Supplier<Object[], String>() {
                @Override
                public String get(Object[] input) {
                    return StringTemplates.formatWithPlaceholder("can't find the <artifactId/> in the doc {}", input);
                }
            }, this.pomPath);
            String artifactId = artifactElement.getTextContent().trim();
            return new GAV(groupId, artifactId, version);
        } catch (Throwable ex) {
            throw new PomParseException(ex);
        }
    }
}
