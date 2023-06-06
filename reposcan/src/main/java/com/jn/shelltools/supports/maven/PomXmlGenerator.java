package com.jn.shelltools.supports.maven;

import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Strings;
import com.jn.langx.util.Throwables;
import com.jn.langx.util.bean.Beans;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Function;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PomXmlGenerator implements PomGenerator {

    private Configuration freemarkerConfiguration;

    @Override
    public String get(MavenPackageArtifact pomModel) {
        try {
            MavenPackageArtifact formatted = format(pomModel);
            Template template = freemarkerConfiguration.getTemplate("pom.xml-model4.0.0.ftl");
            StringWriter stringWriter = new StringWriter();
            template.process(formatted, stringWriter);
            stringWriter.flush();
            return stringWriter.toString();
        } catch (Throwable e) {
            throw Throwables.wrapAsRuntimeException(e);
        }
    }

    private MavenPackageArtifact format(MavenPackageArtifact model) {
        MavenPackageArtifact ret = new MavenPackageArtifact();
        Beans.copyProperties(model, ret);
        // 处理依赖版本
        final Map<String, String> properties = new LinkedHashMap<String, String>();
        ret.setProperties(properties);

        List<DependencyModel> dependencies = ret.getDependencies();
        dependencies = Pipeline.of(dependencies)
                .map(new Function<DependencyModel, DependencyModel>() {
                    @Override
                    public DependencyModel apply(DependencyModel dependencyModel) {
                        DependencyModel dependency = new DependencyModel();
                        Beans.copyProperties(dependencyModel, dependency);
                        return dependency;
                    }
                })
                .map(new Function<DependencyModel, DependencyModel>() {
                    @Override
                    public DependencyModel apply(DependencyModel dependency) {
                        String version = dependency.getVersion();
                        String property;
                        if (Strings.isNotEmpty(dependency.getProjectName())) {
                            property = StringTemplates.formatWithPlaceholder("{}.{}.version", dependency.getProjectName(), dependency.getGroupId());
                        } else {
                            property = StringTemplates.formatWithPlaceholder("{}.version", dependency.getGroupId());
                        }
                        properties.putIfAbsent(property, version);
                        dependency.setVersion("${" + property + "}");
                        return dependency;
                    }
                }).asList();
        ret.setDependencies(dependencies);
        ret.setProperties(properties);
        return ret;
    }


    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }
}
