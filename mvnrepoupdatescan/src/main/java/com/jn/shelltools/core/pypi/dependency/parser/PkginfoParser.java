package com.jn.shelltools.core.pypi.dependency.parser;

import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.io.Charsets;

import java.io.File;
import java.util.List;

/**
 * PKG-INFO 文件，METADATA 文件都可以用它来解析
 */
public class PkginfoParser implements DependenciesParser {

    @Override
    public List<String> parse(File file) {
        Resource resource = Resources.loadFileResource(file);
        List<String> dependencies = Collects.emptyArrayList();
        Resources.readLines(resource, Charsets.UTF_8, new Consumer<String>() {
            @Override
            public void accept(String line) {
                boolean isDependencyLine = false;
                if (line.startsWith("Requires-Dist: ")) {
                    line = line.substring("Requires-Dist: ".length());
                    isDependencyLine = true;
                }
                if (line.startsWith("Provides-Extra: ")) {
                    line = line.substring("Provides-Extra: ".length());
                    isDependencyLine = true;
                }
                if (isDependencyLine) {
                    int index = line.indexOf(";");
                    if (index != -1) {
                        line = line.substring(0, index);
                    }
                    line = line.trim();
                    line = line.replace("(", "").replace(")", "");
                    dependencies.add(line);
                }
            }
        });

        return dependencies;
    }
}
