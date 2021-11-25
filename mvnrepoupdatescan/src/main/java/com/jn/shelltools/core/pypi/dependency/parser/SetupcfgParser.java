package com.jn.shelltools.core.pypi.dependency.parser;

import ca.szc.configparser.Ini;
import com.jn.langx.io.resource.ByteArrayResource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Functions;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.logging.Loggers;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * https://setuptools.pypa.io/en/latest/userguide/declarative_config.html
 * <p>
 * https://mvnrepository.com/artifact/ca.szc.configparser/java-configparser/0.2
 */
public class SetupcfgParser implements DependenciesParser {
    private static final Logger logger = Loggers.getLogger(SetupcfgParser.class);

    @Override
    public List<String> parse(File file) {
        final List<String> dependencies = Collects.emptyArrayList();
        try {
            Ini ini = new Ini().read(Paths.get(file.toURI()));

            // 从 metadata 中找到 require
            Map<String, String> metadata = ini.getSections().get("metadata");
            if (metadata != null) {
                String requiresString = metadata.get("requires"); // list-comma
                List<String> requires = parseDanglingListOrComma(requiresString);
                dependencies.addAll(requires);
            }

            Map<String, String> options = ini.getSections().get("options");
            if (options != null) {
                String setup_requiresString = options.get("setup_requires");
                List<String> setup_requires = parseDanglingListOrSemicolon(setup_requiresString);
                dependencies.addAll(setup_requires);

                String install_requiresString = options.get("install_requires");
                List<String> install_requires = parseDanglingListOrSemicolon(install_requiresString);
                dependencies.addAll(install_requires);

                String tests_requireString = options.get("tests_require");
                List<String> tests_requires = parseDanglingListOrSemicolon(tests_requireString);
                dependencies.addAll(tests_requires);
            }

            Map<String, String> extras_require = ini.getSections().get("options.extras_require");
            Collects.forEach(extras_require.values(), new Consumer<String>() {
                @Override
                public void accept(String requireString) {
                    List<String> extra_requires = parseDanglingListOrSemicolon(requireString);
                    dependencies.addAll(extra_requires);
                }
            });
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
        }
        return dependencies;
    }


    private List<String> parseDanglingListOrComma(String str) {
        if(Strings.isBlank(str)){
            return Collects.immutableList();
        }
        if (str.contains("\n") || str.contains("#")) {
            final List<String> deps = Collects.emptyArrayList();
            Resources.readLines(new ByteArrayResource(str.getBytes(Charsets.UTF_8)), Charsets.UTF_8, new Consumer<String>() {
                @Override
                public void accept(String line) {
                    if(Strings.isBlank(line)){
                        return;
                    }
                    // 截取 # 之前的内容
                    int index = line.indexOf("#");
                    if (index != -1) {
                        line = line.substring(0, index);
                    }
                    // 截取 ; 之前
                    index = line.indexOf(";");
                    if (index != -1) {
                        line = line.substring(0, index);
                    }
                    if (Strings.isNotBlank(line)) {
                        deps.add(line);
                    }
                }
            });
            return deps;
        } else {
            String[] segments = Strings.split(str, ",");
            return Pipeline.of(segments)
                    .filter(Functions.notEmptyPredicate())
                    .collect(Collects.toList());
        }
    }

    private List<String> parseDanglingListOrSemicolon(String str) {
        if(Strings.isBlank(str)){
            return Collects.immutableList();
        }
        if (str.contains("\n") || str.contains("#")) {
            final List<String> deps = Collects.emptyArrayList();
            Resources.readLines(new ByteArrayResource(str.getBytes(Charsets.UTF_8)), Charsets.UTF_8, new Consumer<String>() {
                @Override
                public void accept(String line) {
                    if(Strings.isBlank(line)){
                        return;
                    }
                    // 截取 # 之前的内容
                    int index = line.indexOf("#");
                    if (index != -1) {
                        line = line.substring(0, index);
                    }
                    // 截取 ; 之前
                    index = line.indexOf(";");
                    if (index != -1) {
                        line = line.substring(0, index);
                    }
                    if (Strings.isNotBlank(line)) {
                        deps.add(line);
                    }
                }
            });
            return deps;
        } else {
            String[] segments = Strings.split(str, ";");
            return Pipeline.of(segments)
                    .filter(Functions.notEmptyPredicate())
                    .collect(Collects.toList());
        }
    }
}
