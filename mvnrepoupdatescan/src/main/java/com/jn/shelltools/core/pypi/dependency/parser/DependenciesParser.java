package com.jn.shelltools.core.pypi.dependency.parser;

import com.jn.langx.Parser;

import java.io.File;
import java.util.List;

public interface DependenciesParser extends Parser<File, List<String>> {
    @Override
    List<String> parse(File file);
}
