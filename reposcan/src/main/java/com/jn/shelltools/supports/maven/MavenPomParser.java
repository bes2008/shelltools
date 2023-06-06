package com.jn.shelltools.supports.maven;

import com.jn.langx.Parser;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.text.xml.Namespaces;
import com.jn.langx.text.xml.NodeListIterator;
import com.jn.langx.text.xml.XmlAccessor;
import com.jn.langx.text.xml.Xmls;
import com.jn.langx.text.xml.errorhandler.IgnoreErrorHandler;
import com.jn.langx.util.Emptys;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.function.Supplier;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.logging.Loggers;
import com.jn.shelltools.supports.maven.model.License;
import com.jn.shelltools.supports.maven.model.MavenGAV;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MavenPomParser implements Parser<Document, MavenPackageArtifact> {
    private static final Map<String, String> gavXPathMap = new HashMap<>();
    private static final Logger logger = Loggers.getLogger(MavenPomParser.class);

    static {
        gavXPathMap.put("groupIdXPath", "/project/groupId");
        gavXPathMap.put("artifactIdXPath", "/project/artifactId");
        gavXPathMap.put("nameXPath", "/project/name");
        gavXPathMap.put("versionXPath", "/project/version");
        gavXPathMap.put("parentGroupIdXPath", "/project/parent/groupId");
        gavXPathMap.put("parentVersionXPath", "/project/parent/version");
    }

    private String pomPath;

    public MavenPomParser(String pomPath) {
        this.pomPath = pomPath;
    }

    public static String getGavXpath(String key, boolean usingCustomNamespace, String namespacePrefix) {
        String xpath = gavXPathMap.get(key);

        if (usingCustomNamespace && Emptys.isNotEmpty(xpath)) {
            return useNamespacePrefix(xpath, namespacePrefix);
        }
        return xpath;
    }

    private static String useNamespacePrefix(String xpath, final String namespacePrefix) {
        String[] segments = Strings.split(xpath, "/");
        List<String> prefixedSegments = Pipeline.of(segments).clearNulls().map(new Function<String, String>() {
            @Override
            public String apply(String segment) {
                return namespacePrefix + ":" + segment;
            }
        }).asList();
        return "/" + Strings.join("/", prefixedSegments);
    }

    @Override
    public MavenPackageArtifact parse(Document pom) {
        MavenPackageArtifact mavenArtifact = new MavenPackageArtifact();
        MavenGAV gav = parseGav(pom);
        mavenArtifact.setGav(gav);
        List<License> licenses = parseLicenses(pom);
        mavenArtifact.setLicenses(licenses);
        return mavenArtifact;
    }

    public static MavenPackageArtifact parsePom(File pomFile) {
        if (pomFile == null) {
            return null;
        }
        FileInputStream inputStream = null;
        MavenPackageArtifact mavenArtifact = null;
        String path = pomFile.getAbsolutePath();
        try {
            inputStream = new FileInputStream(pomFile);
            Document document = Xmls.getXmlDoc(null, new IgnoreErrorHandler(), inputStream);
            mavenArtifact = new MavenPomParser(path).parse(document);
        } catch (Throwable ex) {
            logger.error("Error occur when parse {} , error: {}", path, ex.getMessage(), ex);
        } finally {
            IOs.close(inputStream);
        }
        return mavenArtifact;
    }

    private MavenGAV parseGav(Document doc) {
        boolean usingCustomNamespace = Namespaces.hasCustomNamespace(doc, true);
        String namespacePrefix = "x";
        String groupIdXPath = getGavXpath("groupIdXPath", usingCustomNamespace, namespacePrefix);
        String artifactIdXPath = getGavXpath("artifactIdXPath", usingCustomNamespace, namespacePrefix);
        String nameXPath = getGavXpath("nameXPath", usingCustomNamespace, namespacePrefix);
        String versionXPath = getGavXpath("versionXPath", usingCustomNamespace, namespacePrefix);
        String parentGroupIdXPath = getGavXpath("parentGroupIdXPath", usingCustomNamespace, namespacePrefix);
        String parentVersionXPath = getGavXpath("parentVersionXPath", usingCustomNamespace, namespacePrefix);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XmlAccessor xmlAccessor = usingCustomNamespace ? new XmlAccessor(namespacePrefix) : new XmlAccessor();
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
            return new MavenGAV(groupId, artifactId, version);
        } catch (Throwable ex) {
            throw new PomParseException(ex);
        }
    }

    private List<License> parseLicenses(Document doc) {
        boolean usingCustomNamespace = Namespaces.hasCustomNamespace(doc);
        String namespacePrefix = "x";
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XmlAccessor xmlAccessor = usingCustomNamespace ? new XmlAccessor(namespacePrefix) : new XmlAccessor();

        String xpath = "/licenses/license";
        if (usingCustomNamespace) {
            xpath = useNamespacePrefix(xpath, namespacePrefix);
        }
        List<License> licenses = Collects.emptyArrayList();
        try {
            NodeList licenseNodes = xmlAccessor.getNodeList(doc, xPathFactory, xpath);
            Collects.forEach(new NodeListIterator(licenseNodes), new Consumer<Node>() {
                @Override
                public void accept(Node node) {
                    if (node instanceof Element) {
                        Element licenseElement = (Element) node;
                        NodeList children = licenseElement.getChildNodes();
                        final License license = new License();
                        Collects.forEach(new NodeListIterator(children), new Consumer<Node>() {
                            @Override
                            public void accept(Node node) {
                                if (node instanceof Element) {
                                    Element element = (Element) node;
                                    String tag = element.getTagName();
                                    if ("name".equalsIgnoreCase(tag)) {
                                        license.setName(element.getTextContent());
                                    } else if ("url".equalsIgnoreCase(tag)) {
                                        license.setUrl(element.getTextContent());
                                    } else if ("distribution".equalsIgnoreCase(tag)) {
                                        license.setDistribution(element.getTextContent());
                                    } else if ("comments".equalsIgnoreCase(tag)) {
                                        license.setComments(element.getTextContent());
                                    }
                                }
                            }
                        });

                        licenses.add(license);
                    }
                }
            });
        } catch (Throwable ex) {
            throw new PomParseException(ex);
        }
        return licenses;
    }

}
