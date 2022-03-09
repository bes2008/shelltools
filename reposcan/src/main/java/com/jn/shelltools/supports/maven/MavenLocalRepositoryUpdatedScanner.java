package com.jn.shelltools.supports.maven;

import com.jn.langx.Filter;
import com.jn.langx.annotation.NonNull;
import com.jn.langx.io.resource.DirectoryBasedFileResourceLoader;
import com.jn.langx.text.xml.resolver.NullEntityResolver;
import com.jn.langx.util.Maths;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.io.file.Filenames;
import com.jn.langx.util.io.file.filter.AnyFileFilter;
import com.jn.langx.util.io.file.filter.FilenameSuffixFilter;
import com.jn.langx.util.io.file.filter.IsDirectoryFileFilter;
import com.jn.langx.util.io.file.filter.IsFileFilter;
import com.jn.langx.util.struct.Holder;
import com.jn.shelltools.supports.maven.model.MavenGAV;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MavenLocalRepositoryUpdatedScanner {
    private static final Logger logger = LoggerFactory.getLogger(MavenLocalRepositoryUpdatedScanner.class);

    public Map<MavenGAV, MavenPackageArtifact> scan(File directory, Filter<MavenPackageArtifact> filter) {
        Map<MavenGAV, MavenPackageArtifact> artifactMap = scan(new HashMap<>(), new HashMap<>(), new Holder<File>(), directory, filter);
        return artifactMap;
    }

    /**
     * @param readPoms             已经读过的pom文件，内部会扫描 pom文件，这个操作比较耗时，使用它来尽量避免一个pom被多次扫描
     *                             key 是 pom的绝对路径
     *                             value 是 GAV
     * @param readMavenArtifactMap 已经读过的Artifact
     * @param rootDirectory        本地仓库的根目录
     * @param directory            本地仓库下的某个目录，绝对路径
     * @param filter               进行匹配
     * @return 匹配的artifacts
     */
    private Map<MavenGAV, MavenPackageArtifact> scan(Map<String, MavenGAV> readPoms, Map<MavenGAV, MavenPackageArtifact> readMavenArtifactMap, @NonNull Holder<File> rootDirectory, File directory, Filter<MavenPackageArtifact> filter) {
        Map<MavenGAV, MavenPackageArtifact> map = new TreeMap<MavenGAV, MavenPackageArtifact>();
        DirectoryBasedFileResourceLoader resourceLoader = new DirectoryBasedFileResourceLoader(directory.getAbsolutePath());
        List<File> files = resourceLoader.listFiles(new AnyFileFilter(Collects.asList(
                new IsFileFilter(),
                new IsDirectoryFileFilter(),
                new FilenameSuffixFilter("pom", true),
                new FilenameSuffixFilter("jar", true)
        )));
        Collects.forEach(files, new Consumer<File>() {
            @Override
            public void accept(File file) {
                if (file.isDirectory()) {
                    map.putAll(scan(readPoms, readMavenArtifactMap, rootDirectory, file, filter));
                } else {
                    File pomFile = null;
                    if (file.getName().endsWith(".jar")) {
                        String version = file.getParentFile().getName();
                        String artifactId = file.getParentFile().getParentFile().getName();
                        String pomFilePrefix = artifactId + "-" + version;
                        pomFile = new File(file.getParentFile(), pomFilePrefix + ".pom");
                    } else if (file.getName().endsWith(".pom")) {
                        pomFile = file;
                    } else {
                        return;
                    }
                    if (pomFile.exists()) {
                        MavenPackageArtifact mavenArtifact = null;
                        String absolutePath = pomFile.getAbsolutePath();
                        if (!readPoms.containsKey(absolutePath)) {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = new FileInputStream(pomFile);
                                Document document = getXmlDoc(null, inputStream, new IgnoreErrorHandler());
                                mavenArtifact = new MavenPomParser(absolutePath).parse(document);

                                if (rootDirectory.get() == null) {
                                    String filepath = Filenames.asUnixFilePath(absolutePath);
                                    String rootPath = filepath.replace(filepath, mavenArtifact.getGav().getPomPath());
                                    rootDirectory.set(new File(rootPath));
                                }
                                readPoms.put(absolutePath, mavenArtifact.getGav());
                            } catch (Throwable ex) {
                                logger.error("Error occur when parse {} , error: {}", absolutePath, ex.getMessage());
                            } finally {
                                IOs.close(inputStream);
                            }
                        } else {
                            MavenGAV gav = readPoms.get(absolutePath);
                            mavenArtifact = readMavenArtifactMap.get(gav);
                        }

                        if (mavenArtifact != null) {
                            // do filter
                            final Holder<Long> lastModified = new Holder<Long>(pomFile.lastModified());
                            String[] relatedFileSuffixes = {"-javadoc.jar", "-tests.jar", "-sources.jar"};
                            String artifactPrefix = pomFile.getAbsolutePath().substring(0, pomFile.getAbsolutePath().length() - ".pom".length());
                            Collects.forEach(relatedFileSuffixes, new Consumer<String>() {
                                @Override
                                public void accept(String relatedFileSuffix) {
                                    File relatedFile = new File(artifactPrefix + relatedFileSuffix);
                                    if (relatedFile.exists()) {
                                        lastModified.set(Maths.maxLong(lastModified.get(), relatedFile.lastModified()));
                                    }
                                }
                            });
                            mavenArtifact.setLastModifiedTime(lastModified.get());
                            mavenArtifact.setLocalPath(pomFile.getParentFile().getAbsolutePath());
                            if (filter.accept(mavenArtifact)) {
                                map.put(mavenArtifact.getGav(), mavenArtifact);
                            }
                        }
                    } else {
                        logger.warn("can't find the pom file for {}", file.getAbsoluteFile());
                    }
                }
            }
        });
        return map;
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


    public static Document getXmlDoc(EntityResolver entityResolver, final InputStream xml, ErrorHandler errorHandler) throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(false);
        factory.setIgnoringElementContentWhitespace(false);
        factory.setNamespaceAware(true);
        if (entityResolver != null) {
            factory.setValidating(true);
        }
        final DocumentBuilder builder = factory.newDocumentBuilder();
        entityResolver = ((entityResolver == null) ? new NullEntityResolver() : entityResolver);
        builder.setEntityResolver(entityResolver);
        builder.setErrorHandler(errorHandler);
        return builder.parse(xml);
    }
}
