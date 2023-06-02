package com.jn.shelltools.supports.gradle;

import com.jn.langx.Parser;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.text.StrTokenizer;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.regexp.Regexp;
import com.jn.langx.util.regexp.Regexps;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import org.apache.commons.compress.utils.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradleProjectDependenciesParser implements Parser<Resource, List<DependencyModel>> {

    private static Regexp LIBRARY_HEADER = Regexps.compile("library\\s*?\\(\\s*?\"(?<LIBNAME>.*)\"\\s*?,\\s*?\"(?<LIBVERSION>.*)\"\\s*?\\).*\\{.*");
    private static Regexp LIBRARY_GROUP = Regexps.compile("group\\s*?\\(\\s*?\"(?<LIBGROUP>.*)\"\\s*?\\).*\\{.*");

    private static Regexp LIBRARY_MODULES_START = Regexps.compile("modules\\s*=\\s*\\[");


    public List<DependencyModel> parse(Resource resource) {

        final List<DependencyModel> ret = Lists.newArrayList();
        List<String> lines = Resources.readLines(resource, Charsets.UTF_8);


        boolean inLibraryScope = false;
        boolean inLibGroupScope = false;
        boolean inLibModulesScope = false;
        boolean isLibModulesEnd = true;

        String libraryName = null;
        String libraryVersion = null;
        String libraryGroup = null;
        StringBuilder libraryModulesString = null;

        for (String currentLine : lines) {
            String line = Strings.trim(currentLine);
            if (Strings.isBlank(line)) {
                continue;
            }
            if (!inLibraryScope) {
                if (Regexps.match(LIBRARY_HEADER, line)) {
                    inLibraryScope = true;
                    Map<String, String> map = Regexps.findNamedGroup(LIBRARY_HEADER, line);
                    libraryName = map.get("LIBNAME");
                    libraryVersion = map.get("LIBVERSION");
                    continue;
                }
            }
            // 在 lib 范围下，找 group
            if (!inLibGroupScope) {
                if (Regexps.match(LIBRARY_GROUP, line)) {
                    inLibGroupScope = true;
                    Map<String, String> map = Regexps.findNamedGroup(LIBRARY_GROUP, line);
                    libraryGroup = map.get("LIBGROUP");
                    continue;
                }
            }

            // 在group 下，找 modules
            if (!inLibModulesScope) {
                if (Regexps.match(LIBRARY_MODULES_START, line)) {
                    inLibModulesScope = true;
                    isLibModulesEnd = false;
                    libraryModulesString = new StringBuilder();
                }
            }
            if (inLibModulesScope) {
                libraryModulesString.append(line);
                if (line.contains("]")) {
                    isLibModulesEnd = true;
                }

                if (isLibModulesEnd) {
                    // 解析 modules
                    String modulesString = libraryModulesString.toString();
                    int start = Strings.indexOf(modulesString, "[") + 1;
                    int end = Strings.lastIndexOf(modulesString, "]");
                    modulesString = Strings.substring(modulesString, start, end);
                    // 对 exclude 中的 【,】 替换成 【::】 ，并且将 exclude 和 引号 移除
                    if (modulesString.contains("exclude")) {
                        StringBuilder tmpModulesString = new StringBuilder(0);
                        String[] delimiters = {"{", "exclude", "}"};
                        StrTokenizer tokenizer = new StrTokenizer(modulesString, true, delimiters);
                        boolean inExcludeScope = false;
                        while (tokenizer.hasNext()) {
                            String token = Strings.trim(tokenizer.next());
                            if ("{".equals(token)) {
                                inExcludeScope = true;
                                tmpModulesString.append("{");
                                continue;
                            }
                            if ("exclude".equals(token)) {
                                continue;
                            }
                            if ("}".equals(token)) {
                                tmpModulesString.append("}");
                                inExcludeScope = false;
                                continue;
                            }
                            if (inExcludeScope) {
                                token = token.replace(",", "::") + "|";
                                token = token.replace("\"", "");
                                tmpModulesString.append(token);
                            } else {
                                token = token.replace("\"", "");
                                tmpModulesString.append(token);
                            }
                        }
                        modulesString = tmpModulesString.toString();
                    }
                    List<String> moduleNames = Pipeline.of(Strings.split(modulesString, ",")).map(new Function<String, String>() {
                        @Override
                        public String apply(String moduleName) {
                            if (Strings.startsWith(moduleName, "\"")) {
                                moduleName = moduleName.substring(1);
                            }

                            if (Strings.endsWith(moduleName, "\"")) {
                                moduleName = moduleName.substring(0, moduleName.length() - 1);
                            }

                            return moduleName;
                        }
                    }).asList();

                    for (String moduleName : moduleNames) {
                        List<PackageGAV> excludes = null;
                        if (moduleName.contains("{") && moduleName.contains("}")) {
                            int leftBrace = Strings.indexOf(moduleName, "{");
                            int rightBrace = Strings.lastIndexOf(moduleName, "}");
                            String excludeString = Strings.substring(moduleName, leftBrace + 1, rightBrace);
                            moduleName = Strings.substring(moduleName, 0, leftBrace);
                            if (Strings.isNotBlank(excludeString)) {
                                excludes = Pipeline.of(Strings.split(excludeString, "|"))
                                        .map(new Function<String, PackageGAV>() {
                                            @Override
                                            public PackageGAV apply(String exclude) {
                                                String[] segments = Strings.split(exclude, "::");
                                                Map<String, String> map = new HashMap<>();
                                                for (String segment : segments) {
                                                    String[] keyValue = Strings.split(segment, ":");
                                                    if (keyValue.length == 2) {
                                                        map.put(keyValue[0], keyValue[1]);
                                                    }
                                                }
                                                PackageGAV excludeGav = new PackageGAV(map.get("group"), map.get("module"), null);
                                                return excludeGav;
                                            }
                                        }).asList();
                            }
                        }
                        DependencyModel dependency = new DependencyModel(libraryGroup, moduleName, libraryVersion);
                        dependency.setExcludes(excludes);
                        ret.add(dependency);
                    }

                    // 重置变量
                    inLibraryScope = false;
                    inLibGroupScope = false;
                    inLibModulesScope = false;
                    libraryModulesString = null;
                    isLibModulesEnd = false;
                }
            }
        }
        return ret;
    }
}
