package com.jn.shelltools.core.maven;

import com.jn.langx.Filter;
import com.jn.langx.annotation.NonNull;
import com.jn.langx.io.resource.DirectoryBasedFileResourceLoader;
import com.jn.langx.text.xml.Xmls;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.io.file.Filenames;
import com.jn.langx.util.io.file.filter.AnyFileFilter;
import com.jn.langx.util.io.file.filter.FilenameSuffixFilter;
import com.jn.langx.util.io.file.filter.IsDirectoryFileFilter;
import com.jn.langx.util.io.file.filter.IsFileFilter;
import com.jn.langx.util.struct.Holder;
import com.jn.shelltools.core.maven.model.GAV;
import com.jn.shelltools.core.maven.model.MavenArtifact;
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

    public Map<GAV, MavenArtifact> scan(File directory, Filter<MavenArtifact> filter) {
        Map<GAV, MavenArtifact> artifactMap = scan(new HashMap<>(), new HashMap<>(), new Holder<File>(), directory, filter);
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
    private Map<GAV, MavenArtifact> scan(Map<String, GAV> readPoms, Map<GAV, MavenArtifact> readMavenArtifactMap, @NonNull Holder<File> rootDirectory, File directory, Filter<MavenArtifact> filter) {
        Map<GAV, MavenArtifact> map = new TreeMap<GAV, MavenArtifact>();
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
                        String pomFilePrefix = null;
                        if (file.getName().endsWith("-javadoc.jar")) {
                            pomFilePrefix = file.getName().substring(0, file.getName().length() - ".javadoc.jar".length());
                        } else if (file.getName().endsWith("-tests.jar")) {
                            pomFilePrefix = file.getName().substring(0, file.getName().length() - ".tests.jar".length());
                        } else if (file.getName().endsWith("-sources.jar")) {
                            pomFilePrefix = file.getName().substring(0, file.getName().length() - ".sources.jar".length());
                        } else if (file.getName().endsWith(".jar")) {
                            pomFilePrefix = file.getName().substring(0, file.getName().length() - ".jar".length());
                        }
                        pomFile = new File(file.getParentFile(), pomFilePrefix + ".pom");
                    } else if (file.getName().endsWith(".pom")) {
                        pomFile = file;
                    } else{
                        return;
                    }
                    if (pomFile.exists()) {
                        MavenArtifact mavenArtifact = null;
                        if (!readPoms.containsKey(pomFile.getAbsolutePath())) {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = new FileInputStream(pomFile);
                                Document document = getXmlDoc(null, inputStream, new IgnoreErrorHandler());
                                mavenArtifact = new MavenArtifactPomParser(pomFile.getAbsolutePath()).parse(document);

                                if (rootDirectory.get() == null) {
                                    String filepath = Filenames.asUnixFilePath(pomFile.getAbsolutePath());
                                    String rootPath = filepath.replace(filepath, mavenArtifact.getGav().getPomPath());
                                    rootDirectory.set(new File(rootPath));
                                }
                                readPoms.put(pomFile.getAbsolutePath(), mavenArtifact.getGav());
                            } catch (Throwable ex) {
                                logger.error("Error occur when parse {} , error: {}", pomFile.getAbsolutePath(), ex.getMessage(), ex);
                            } finally {
                                IOs.close(inputStream);
                            }
                        } else {
                            GAV gav = readPoms.get(pomFile.getAbsolutePath());
                            mavenArtifact = readMavenArtifactMap.get(gav);
                        }

                        if (mavenArtifact != null) {
                            // do filter
                            mavenArtifact.setLastModifiedTime(pomFile.lastModified());
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

    private static class IgnoreErrorHandler implements ErrorHandler{
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
        entityResolver = ((entityResolver == null) ? new Xmls.NullEntityResolver() : entityResolver);
        builder.setEntityResolver(entityResolver);
        builder.setErrorHandler(errorHandler);
        return builder.parse(xml);
    }
}