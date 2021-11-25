package com.jn.shelltools.core.pypi.dependency.parser;

import com.jn.langx.util.logging.Loggers;
import org.eclipse.steady.python.Python3FileAnalyzer;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;

public class SetuppyParser implements DependenciesParser{
    private static final Logger logger = Loggers.getLogger(SetuppyParser.class);
    @Override
    public List<String> parse(File file) {
        try {
            Python3FileAnalyzer analyzer = new Python3FileAnalyzer();
            if (analyzer.canAnalyze(file)) {
                analyzer.analyze(file);
                if (analyzer.hasChilds()) {
                    //    analyzer.enter
                }
            }

        }catch (Throwable ex){
            logger.error(ex.getMessage(),ex);
        }
        return null;
    }
}
