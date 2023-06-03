package com.jn.shelltools.supports.maven;

import com.jn.langx.text.StringTemplates;
import com.jn.langx.util.Strings;
import com.jn.langx.util.bean.Beans;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Function;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import com.jn.shelltools.supports.maven.model.PomModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PomXmlGenerator implements PomGenerator {

    @Override
    public String get(PomModel pomModel) {
        PomModel formatted = format(pomModel);
        return null;
    }

    private PomModel format(PomModel model) {
        PomModel ret = new PomModel();
        Beans.copyProperties(model, ret);
        // 处理依赖版本
        final Map<String, String> properties = new LinkedHashMap<String, String>();
        ret.setProperties(properties);

        List<DependencyModel> dependencies = ret.getDependencies();
        dependencies = Pipeline.of(dependencies)
                .map(new Function<DependencyModel, DependencyModel>() {
                    @Override
                    public DependencyModel apply(DependencyModel dependencyModel) {
                        DependencyModel dependency  = new DependencyModel();
                        Beans.copyProperties(dependencyModel, dependency);
                        return dependency;
                    }
                })
                .map(new Function<DependencyModel, DependencyModel>() {
                    @Override
                    public DependencyModel apply(DependencyModel dependency) {
                        String version = dependency.getVersion();
                        String property = null;
                        if(Strings.isNotEmpty(dependency.getProjectName())) {
                            property = StringTemplates.formatWithPlaceholder("{}.{}.version", dependency.getProjectName(), dependency.getGroupId());
                        }else{
                            property = StringTemplates.formatWithPlaceholder("{}.version", dependency.getGroupId());
                        }
                        properties.putIfAbsent(property, version);
                        dependency.setVersion("${"+property+"}");
                        return dependency;
                    }
                }).asList();
        ret.setDependencies(dependencies);
        ret.setProperties(properties);
        return ret;
    }
}
