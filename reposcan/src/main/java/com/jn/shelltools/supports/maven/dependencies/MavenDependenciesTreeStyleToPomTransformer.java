package com.jn.shelltools.supports.maven.dependencies;

import com.jn.langx.Transformer;
import com.jn.langx.io.resource.Resource;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.PomXmlGenerator;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import com.jn.shelltools.supports.maven.model.PomModel;
import freemarker.template.Configuration;

import java.util.List;

public class MavenDependenciesTreeStyleToPomTransformer implements Transformer<Resource, String> {
    private PackageGAV packageGav;
    private Configuration freemarkerConfig;

    public static String transform(Resource resource, PackageGAV packageGav, Configuration freemarkerConfig){
        MavenDependenciesTreeStyleToPomTransformer transformer = new MavenDependenciesTreeStyleToPomTransformer();
        transformer.setFreemarkerConfig(freemarkerConfig);
        transformer.setPackageGav(packageGav);
        return transformer.transform(resource);
    }

    @Override
    public String transform(Resource resource) {
        MavenDependenciesTreeStyleDependenciesParser parser = new MavenDependenciesTreeStyleDependenciesParser();
        List<DependencyModel> dependencyModels = parser.parse(resource);

        PomModel pomModel = new PomModel(packageGav.getGroupId(), packageGav.getArtifactId(), packageGav.getVersion());
        pomModel.setDependencies(dependencyModels);
        PomXmlGenerator generator = new PomXmlGenerator();
        generator.setFreemarkerConfiguration(freemarkerConfig);
        String pomXml = generator.get(pomModel);
        return pomXml;
    }

    public PackageGAV getPackageGav() {
        return packageGav;
    }

    public void setPackageGav(PackageGAV packageGav) {
        this.packageGav = packageGav;
    }

    public Configuration getFreemarkerConfig() {
        return freemarkerConfig;
    }

    public void setFreemarkerConfig(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }
}
