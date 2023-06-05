package com.jn.shelltools.supports.maven.dependencies;

import com.jn.langx.Parser;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.Emptys;
import com.jn.langx.util.Objs;
import com.jn.langx.util.collection.Lists;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.regexp.Regexp;
import com.jn.langx.util.regexp.Regexps;
import com.jn.shelltools.supports.maven.model.DependencyModel;

import java.util.*;


public class MavenDependenciesTreeStyleDependenciesParser implements Parser<Resource, List<DependencyModel>> {

    // |((|\s+)+\\[-]+)

    //

    public static final Regexp dependencyExpr = Regexps.compile("(?:((\\|)?\\s+)*?[+\\\\]-+)?(\\s+)?(?<groupId>[^:'\"\\(* \\t]+)\\:(?<artifactId>[^:'\"\\(* \\t]+)\\:(?<version>[^:'\"\\(* \\t]+)(\\s+.*)?");


    public List<DependencyModel> parse(Resource resource) {

        final List<DependencyModel> ret = Lists.newArrayList();
        List<String> lines = Resources.readLines(resource, Charsets.UTF_8);
        Pipeline.of(lines)
                .forEach(line -> Regexps.match(dependencyExpr, line), new Consumer<String>() {
                    @Override
                    public void accept(String line) {
                        Map<String, String> map=  Regexps.findNamedGroup(dependencyExpr, line);
                        if(Objs.isNotEmpty(map)) {
                            String groupId = map.get("groupId");
                            String artifactId = map.get("artifactId");
                            String version = map.get("version");

                            if(Emptys.isNoneEmpty(groupId, artifactId, version)){
                                DependencyModel dependency = new DependencyModel(groupId, artifactId, version);
                                ret.add(dependency);
                            }
                        }
                    }
                });
        return ret;
    }


}
