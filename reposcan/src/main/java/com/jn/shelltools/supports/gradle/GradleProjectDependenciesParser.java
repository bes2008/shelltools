package com.jn.shelltools.supports.gradle;

import com.jn.langx.Parser;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.text.StrTokenizer;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.enums.base.CommonEnum;
import com.jn.langx.util.enums.base.EnumDelegate;
import com.jn.langx.util.function.Function;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.regexp.Regexp;
import com.jn.langx.util.regexp.Regexps;
import com.jn.shelltools.core.PackageGAV;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import org.apache.commons.compress.utils.Lists;

import java.util.*;


public class GradleProjectDependenciesParser implements Parser<Resource, List<DependencyModel>> {

    private static String FUNC_HEADER_PATTERN = "\\s*?\\(\\s*?(?<VALUE>\".*\"(\\s*?,\\s*?\".*\")*?)\\s*?\\)\\s*?\\{.*";
    private static Regexp LIBRARY_ELEMENT_START = Regexps.compile("library" + FUNC_HEADER_PATTERN);
    private static Regexp LIBRARY_GROUP_ELEMENT_START = Regexps.compile("group" + FUNC_HEADER_PATTERN);

    private static Regexp LIBRARY_MODULES_START = Regexps.compile("modules\\s*=\\s*\\[");



    static enum ElementType implements CommonEnum {
        LIBRARY(1, "library", ElementStyle.FUNCTION, true),
        GROUP(2, "group", ElementStyle.FUNCTION, true),
        PROHIBIT(3, "prohibit", ElementStyle.OBJECT, true),
        MODULES(4, "modules", ElementStyle.ARRAY, true),
        IMPORTS(5, "imports", ElementStyle.ARRAY, true),
        EXCLUDE(6, "exclude", ElementStyle.TEXT, false),
        PLUGINS(7, "plugins", ElementStyle.ARRAY, true),
        CLASSIFIER(8, "classifier", ElementStyle.TEXT, false);


        private EnumDelegate delegate;
        private ElementStyle style;
        private boolean bodyRequired;

        ElementType(int code, String name, ElementStyle style, boolean bodyRequired) {
            this.delegate = new EnumDelegate(code, name, name);
            this.style = style;
            this.bodyRequired = bodyRequired;
        }

        public ElementStyle getStyle() {
            return style;
        }

        public boolean isBodyRequired() {
            return bodyRequired;
        }

        @Override
        public int getCode() {
            return this.delegate.getCode();
        }

        @Override
        public String getDisplayText() {
            return this.delegate.getDisplayText();
        }

        @Override
        public String getName() {
            return this.delegate.getName();
        }
    }

    static enum ElementStyle {
        TEXT(false,Regexps.compile("\""), Regexps.compile("\"")),
        TAGED_TEXT(false,Regexps.compile("\""), Regexps.compile("\"")),
        FUNCTION(true, Regexps.compile("(?<TAG>[A-Za-z]\\w+?)\\s*?\\(\\s*?(?<VALUE>\".*\"(\\s*?,\\s*?\".*\")*?)\\s*?\\)\\s*?\\{"), Regexps.compile("\\}")),
        ARRAY(true, Regexps.compile("(?<TAG>[A-Za-z]\\w+?)\\s*?=\\s*?\\[\\s*?"), Regexps.compile("\\]")),
        OBJECT(true, Regexps.compile("(?<TAG>[A-Za-z]\\w+?)\\s*?(=\\s*?)?\\{"), Regexps.compile("\\}"));

        private boolean hasBody;
        private Regexp startFlag;
        private Regexp endFlag;

        ElementStyle(boolean hasBody, Regexp startFlag, Regexp endFlag) {
            this.hasBody = hasBody;
            this.startFlag = startFlag;
            this.endFlag = endFlag;
        }

        public boolean isHasBody() {
            return hasBody;
        }

        public void setHasBody(boolean hasBody) {
            this.hasBody = hasBody;
        }

    }

    static class Element {
        private ElementType type;
        private String value;
        private List<String> body;

        public ElementType getType() {
            return type;
        }

        public void setType(ElementType type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<String> getBody() {
            return body;
        }

        public void setBody(List<String> body) {
            this.body = body;
        }
    }

    static interface ElementListener {
        void startElement(Element parent, Element context, String line);

        void endElement(Element parent, Element libraryElement);
    }

    static class LibraryElementListener implements ElementListener{
        @Override
        public void startElement(Element parent, Element libraryElement, String line) {
            Map<String, String> map = Regexps.findNamedGroup(LIBRARY_ELEMENT_START, line);
            String value = map.get("VALUE");
            libraryElement.setValue(value);
            libraryElement.setType(ElementType.LIBRARY);
        }

        @Override
        public void endElement(Element parent, Element libraryElement) {

        }
    }

    static class GroupElementListener implements ElementListener{
        @Override
        public void startElement(Element parent, Element libraryElement, String line) {
            Map<String, String> map = Regexps.findNamedGroup(LIBRARY_ELEMENT_START, line);
            String value = map.get("VALUE");
            libraryElement.setValue(value);
            libraryElement.setType(ElementType.GROUP);
        }

        @Override
        public void endElement(Element parent, Element libraryElement) {

        }
    }

    public List<DependencyModel> parse(Resource resource) {
        final List<DependencyModel> ret = Lists.newArrayList();
        Iterator<String> lines = Resources.readLines(resource, Charsets.UTF_8).iterator();
        Element currentElement = null;
        LibraryElementListener libraryElementListener = new LibraryElementListener();
        while (lines.hasNext()) {
            String currentLine = lines.next();
            String line = Strings.trim(currentLine);
            if (Strings.isBlank(line)) {
                continue;
            }

            if (currentElement == null) {
                if (Regexps.match(LIBRARY_ELEMENT_START, line)) {
                    libraryElementListener.startElement(null, new Element(), line);
                }
                else if(Regexps.match(LIBRARY_GROUP_ELEMENT_START, line)){

                }
            }

        }
        return null;
    }

    public List<DependencyModel> parse2(Resource resource) {

        final List<DependencyModel> ret = Lists.newArrayList();
        List<String> lines = Resources.readLines(resource, Charsets.UTF_8);


        boolean inLibraryScope = false;
        boolean inLibGroupScope = false;
        boolean inLibModulesScope = false;
        boolean isLibModulesEnd = true;
        boolean inLibModuleScope = false;

        String libraryName = null;
        String libraryVersion = null;
        String libraryGroup = null;
        Map<String, List<String>> libraryModulesStringMap = null;

        List<String> moduleBodyString = null;
        for (String currentLine : lines) {
            String line = Strings.trim(currentLine);
            if (Strings.isBlank(line)) {
                continue;
            }
            if (!inLibraryScope) {
                if (Regexps.match(LIBRARY_ELEMENT_START, line)) {
                    inLibraryScope = true;
                    Map<String, String> map = Regexps.findNamedGroup(LIBRARY_ELEMENT_START, line);
                    libraryName = map.get("LIBNAME");
                    libraryVersion = map.get("LIBVERSION");
                    continue;
                }
            }
            // 在 lib 范围下，找 group
            if (!inLibGroupScope) {
                if (Regexps.match(LIBRARY_GROUP_ELEMENT_START, line)) {
                    inLibGroupScope = true;
                    Map<String, String> map = Regexps.findNamedGroup(LIBRARY_GROUP_ELEMENT_START, line);
                    libraryGroup = map.get("LIBGROUP");
                    continue;
                }
            }

            // 在group 下，找 modules
            if (!inLibModulesScope) {
                if (Regexps.match(LIBRARY_MODULES_START, line)) {
                    inLibModulesScope = true;
                    isLibModulesEnd = false;
                    libraryModulesStringMap = new LinkedHashMap<>();
                }
            }
            if (inLibModulesScope) {
                int start = Strings.indexOf(line, "[") + 1;
                line = Strings.substring(line, start);
                if (Strings.isNotEmpty(line)) {
                    int end = Strings.lastIndexOf(line, "]");
                    if (end >= 0) {
                        line = Strings.substring(line, 0, end);
                        isLibModulesEnd = true;
                    }
                }
                if (Strings.isNotEmpty(line)) {
                    if (line.contains("{")) {
                        inLibModuleScope = true;
                        moduleBodyString = new ArrayList<>();
                    }
                    if (line.contains("}") && inLibModuleScope) {
                        int index = Strings.lastIndexOf(line, "}");
                        line = Strings.substring(line, 0, index);
                        if (Strings.isNotEmpty(line)) {
                            moduleBodyString.add(line);
                            inLibModuleScope = false;
                        }
                    }
                    if (inLibModuleScope) {
                        moduleBodyString.add(line);
                    }
                }

                if (isLibModulesEnd) {
                    // 解析 modules
                    String modulesString = libraryModulesStringMap.toString();
                    start = Strings.indexOf(modulesString, "[") + 1;
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
                    libraryModulesStringMap = null;
                    isLibModulesEnd = false;
                }
            }
        }
        return ret;
    }
}
