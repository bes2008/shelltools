package com.jn.shelltools.supports.pypi.dependency.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.jayway.jsonpath.JsonPath;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Functions;
import com.jn.langx.util.logging.Loggers;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;

class TomlDependenciesParser implements DependenciesParser {
    private static final Logger logger = Loggers.getLogger(TomlDependenciesParser.class);

    private String requiresFlag = "requires";

    TomlDependenciesParser() {
        this("requires");
    }

    TomlDependenciesParser(String requiresFlag) {
        this.requiresFlag = requiresFlag;
    }

    @Override
    public List<String> parse(File file) {
        try {
            JsonNode jsonNode = new TomlMapper().readTree(file);
            String json = JSONBuilderProvider.create().prettyFormat(true).build().toJson(jsonNode);
            List requiresList = JsonPath.read(json, "$.." + requiresFlag);
            List<String> dependencies = Collects.emptyArrayList();
            Collects.forEach(requiresList, new Consumer<Object>() {
                @Override
                public void accept(Object o) {
                    String requires = o.toString();
                    requires = Strings.strip(requires, "[]\"'");
                    String[] deps = requires.split("[,\"']");
                    Pipeline.of(deps)
                            .filter(Functions.notEmptyPredicate())
                            .forEach(new Consumer<String>() {
                                @Override
                                public void accept(String dep) {
                                    dependencies.add(dep.trim());
                                }
                            });


                }
            });
            return dependencies;
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
}
