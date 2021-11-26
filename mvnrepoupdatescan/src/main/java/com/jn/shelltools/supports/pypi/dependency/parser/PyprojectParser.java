package com.jn.shelltools.supports.pypi.dependency.parser;

import com.jn.langx.util.logging.Loggers;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;

public class PyprojectParser implements DependenciesParser {
    private static final Logger logger = Loggers.getLogger(PyprojectParser.class);

    @Override
    public List<String> parse(File file) {
        return new TomlDependenciesParser().parse(file);
    }
}
