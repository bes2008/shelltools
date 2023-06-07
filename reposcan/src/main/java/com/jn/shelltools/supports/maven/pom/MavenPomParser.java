package com.jn.shelltools.supports.maven.pom;

import com.jn.langx.Parser;
import com.jn.langx.text.StringTemplates;
import com.jn.langx.text.xml.*;
import com.jn.langx.text.xml.errorhandler.IgnoreErrorHandler;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.enums.Enums;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.function.Supplier;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.logging.Loggers;
import com.jn.shelltools.supports.maven.model.License;
import com.jn.shelltools.supports.maven.model.MavenGAV;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import com.jn.shelltools.supports.maven.model.Packaging;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class MavenPomParser implements Parser<Document, MavenPackageArtifact> {
    private static final Logger logger = Loggers.getLogger(MavenPomParser.class);

    private boolean parseLicenses = false;
    private boolean parsePackaging = false;


    private String pomPath;

    public MavenPomParser(String pomPath) {
        this.pomPath = pomPath;
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
        if (parseLicenses) {
            List<License> licenses = parseLicenses(pom);
            mavenArtifact.setLicenses(licenses);
        }
        if (parsePackaging) {
            Packaging packaging = parsePackaging(pom);
            mavenArtifact.setPackaging(packaging);
        }
        return mavenArtifact;
    }

    public static MavenPackageArtifact parsePom(MavenPomParser.Builder builder, File pomFile) {
        if (pomFile == null) {
            return null;
        }
        FileInputStream inputStream = null;
        MavenPackageArtifact mavenArtifact = null;
        String path = pomFile.getAbsolutePath();
        try {
            inputStream = new FileInputStream(pomFile);
            Document document = Xmls.getXmlDoc(null, new IgnoreErrorHandler(), inputStream);
            mavenArtifact = builder.pomPath(path).build().parse(document);
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
        String groupIdXPath = XPaths.wrapXpath(mappingToXpath("groupId"), usingCustomNamespace, namespacePrefix);
        String artifactIdXPath = XPaths.wrapXpath(mappingToXpath("artifactId"), usingCustomNamespace, namespacePrefix);
        String nameXPath = XPaths.wrapXpath(mappingToXpath("name"), usingCustomNamespace, namespacePrefix);
        String versionXPath = XPaths.wrapXpath(mappingToXpath("version"), usingCustomNamespace, namespacePrefix);
        String parentGroupIdXPath = XPaths.wrapXpath(mappingToXpath("parent.groupId"), usingCustomNamespace, namespacePrefix);
        String parentVersionXPath = XPaths.wrapXpath(mappingToXpath("parent.version"), usingCustomNamespace, namespacePrefix);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XmlAccessor xmlAccessor = new XmlAccessor(usingCustomNamespace ? namespacePrefix : null);
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

    private Packaging parsePackaging(Document doc) {
        Packaging packaging = null;
        try {
            Element element = Xmls.findElement(doc, mappingToXpath("packaging"));
            if (element != null) {
                String text = element.getTextContent();
                if (Strings.isNotEmpty(text)) {
                    packaging = Enums.ofName(Packaging.class, Strings.trim(text));
                    if (packaging == null) {
                        logger.error("parse packaging failed, packaging: {}, file: {}", text, pomPath);
                    }
                }
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        }
        return packaging;
    }


    private List<License> parseLicenses(Document doc) {

        List<License> licenses = Collects.emptyArrayList();
        try {
            NodeList licenseNodes = Xmls.findNodeList(doc, mappingToXpath("licenses.license"));
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


    private static String mappingToXpath(String xpath) {
        xpath = Strings.replace(xpath, ".", "/");
        if (!Strings.startsWith(xpath, "/project/")) {
            xpath = "/project" + (Strings.startsWith(xpath, "/") ? "" : "/") + xpath;
        }
        return xpath;
    }


    public void setParseLicenses(boolean parseLicenses) {
        this.parseLicenses = parseLicenses;
    }

    public void setParsePackaging(boolean parsePackaging) {
        this.parsePackaging = parsePackaging;
    }

    public static class Builder implements com.jn.langx.Builder<MavenPomParser> {
        private String pomPath;
        private boolean parseLicenses;
        private boolean parsePackaging;

        public Builder pomPath(String pomPath) {
            this.pomPath = pomPath;
            return this;
        }


        public Builder parseLicenses(boolean parseLicenses) {
            this.parseLicenses = parseLicenses;
            return this;
        }

        public Builder parsePackaging(boolean parsePackaging) {
            this.parsePackaging = parsePackaging;
            return this;
        }

        @Override
        public MavenPomParser build() {
            MavenPomParser mavenPomParser = new MavenPomParser(pomPath);
            mavenPomParser.setParseLicenses(parseLicenses);
            mavenPomParser.setParsePackaging(parsePackaging);
            return mavenPomParser;
        }
    }
}
