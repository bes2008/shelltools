package com.jn.shelltools.supports.maven.dependencies;

import com.jn.langx.Transformer;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.util.collection.Lists;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.PomXmlGenerator;
import com.jn.shelltools.supports.maven.model.Dependency;
import com.jn.shelltools.supports.maven.model.DependencyManagement;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import com.jn.shelltools.supports.maven.model.Packaging;
import freemarker.template.Configuration;

import java.util.List;

public class MavenDependenciesTreeStyleToPomTransformer implements Transformer<Resource, String> {
    private PackageGAV packageGav;
    private Configuration freemarkerConfig;
    private MavenDependenciesTreeStyleDependenciesParser parser;

    public static String transform(Resource resource, PackageGAV packageGav, Configuration freemarkerConfig) {
        return transform(null, resource, packageGav, freemarkerConfig);
    }

    public static String transform(MavenDependenciesTreeStyleDependenciesParser parser, Resource resource, PackageGAV packageGav, Configuration freemarkerConfig) {
        MavenDependenciesTreeStyleToPomTransformer transformer = new MavenDependenciesTreeStyleToPomTransformer();
        transformer.setParser(parser);
        transformer.setFreemarkerConfig(freemarkerConfig);
        transformer.setPackageGav(packageGav);
        return transformer.transform(resource);
    }

    @Override
    public String transform(Resource resource) {
        if (parser == null) {
            parser = new MavenDependenciesTreeStyleDependenciesParser();
        }
        List<Dependency> dependencyModels = parser.parse(resource);

        MavenPackageArtifact pomModel = new MavenPackageArtifact(packageGav.getGroupId(), packageGav.getArtifactId(), packageGav.getVersion());

        List<Dependency> jarDependencies = Lists.newArrayList();
        List<Dependency> pomDependencies = Lists.newArrayList();

        Pipeline.of(dependencyModels)
                .forEach(new Consumer<Dependency>() {
                    @Override
                    public void accept(Dependency dependency) {
                        if (dependency.getType() == Packaging.POM) {
                            pomDependencies.add(dependency);
                        } else {
                            jarDependencies.add(dependency);
                        }
                    }
                });

        pomModel.setDependencies(jarDependencies);
        pomModel.setDependencyManagement(new DependencyManagement(pomDependencies));
        PomXmlGenerator generator = new PomXmlGenerator();
        generator.setFreemarkerConfiguration(freemarkerConfig);
        String pomXml = generator.get(pomModel);
        return pomXml;
    }

    public void setPackageGav(PackageGAV packageGav) {
        this.packageGav = packageGav;
    }


    public void setFreemarkerConfig(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public MavenDependenciesTreeStyleDependenciesParser getParser() {
        return parser;
    }

    public void setParser(MavenDependenciesTreeStyleDependenciesParser parser) {
        this.parser = parser;
    }

}
