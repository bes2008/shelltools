package com.jn.shelltools.supports.maven;

import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Objs;
import com.jn.langx.util.Strings;
import com.jn.langx.util.Throwables;
import com.jn.langx.util.bean.Beans;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Function;
import com.jn.shelltools.supports.maven.model.Dependency;
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
            Template template = freemarkerConfiguration.getTemplate("pom.xml-model-4.0.0.ftl");
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

        List<Dependency> dependencies = ret.getDependencies();
        dependencies = Pipeline.of(dependencies)
                .map(new Function<Dependency, Dependency>() {
                    @Override
                    public Dependency apply(Dependency dependencyModel) {
                        Dependency dependency = new Dependency();
                        Beans.copyProperties(dependencyModel, dependency);
                        return dependency;
                    }
                })
                .map(new Function<Dependency, Dependency>() {
                    @Override
                    public Dependency apply(Dependency dependency) {
                        String version = dependency.getVersion();
                        String property;
                        if (Strings.isNotEmpty(dependency.getProjectName())) {
                            property = StringTemplates.formatWithPlaceholder("{}.{}.version", dependency.getProjectName(), dependency.getGroupId());
                        } else {
                            property = StringTemplates.formatWithPlaceholder("{}.version", dependency.getGroupId());
                        }
                        properties.putIfAbsent(property, version);
                        String versionInCache = properties.get(property);
                        if(!Objs.equals(versionInCache,version)){
                            if (Strings.isNotEmpty(dependency.getProjectName())) {
                                property = StringTemplates.formatWithPlaceholder("{}.{}.{}.version", dependency.getProjectName(), dependency.getGroupId(), dependency.getArtifactId());
                            } else {
                                property = StringTemplates.formatWithPlaceholder("{}.{}.version", dependency.getGroupId(), dependency.getArtifactId());
                            }
                        }
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
