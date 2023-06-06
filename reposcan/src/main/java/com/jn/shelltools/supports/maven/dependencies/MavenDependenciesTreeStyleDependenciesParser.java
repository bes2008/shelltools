package com.jn.shelltools.supports.maven.dependencies;

import com.jn.langx.Parser;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.Emptys;
import com.jn.langx.util.Objs;
import com.jn.langx.util.collection.Lists;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.enums.Enums;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Predicate;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.regexp.Regexp;
import com.jn.langx.util.regexp.Regexps;
import com.jn.shelltools.supports.maven.MavenPackageManager;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import com.jn.shelltools.supports.maven.model.DependencyScope;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import com.jn.shelltools.supports.maven.model.Packaging;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MavenDependenciesTreeStyleDependenciesParser implements Parser<Resource, List<DependencyModel>> {
    public static final Regexp dependencyExpr = Regexps.compile("(?:((\\|)?\\s+)*?[+\\\\]-+)?(\\s+)?(?<groupId>[^:'\"\\(* \\t]+)\\:(?<artifactId>[^:'\"\\(* \\t]+)\\:(?<version>[^:'\"\\(* \\t]+)(\\:(?<scope>\\w+))?(\\s+.*)?");

    private MavenPackageManager mavenPackageManager;

    public void setMavenPackageManager(MavenPackageManager mavenPackageManager) {
        this.mavenPackageManager = mavenPackageManager;
    }

    public List<DependencyModel> parse(Resource resource) {
        Map<String, DependencyModel> dependencies = new LinkedHashMap<>();
        List<String> lines = Resources.readLines(resource, Charsets.UTF_8);
        Pipeline.of(lines)
                .forEach(line -> Regexps.match(dependencyExpr, line), new Consumer<String>() {
                    @Override
                    public void accept(String line) {
                        Map<String, String> map = Regexps.findNamedGroup(dependencyExpr, line);
                        if (Objs.isNotEmpty(map)) {
                            String groupId = map.get("groupId");
                            String artifactId = map.get("artifactId");
                            String version = map.get("version");
                            String scope = map.get("scope");

                            if (Emptys.isNoneEmpty(groupId, artifactId, version)) {
                                DependencyModel dependency = new DependencyModel(groupId, artifactId, version);
                                dependencies.put(dependency.asId(), dependency);
                                if (Objs.isNotEmpty(scope)) {
                                    DependencyScope dependencyScope = Enums.ofName(DependencyScope.class, scope);
                                    if (dependencyScope != null) {
                                        dependency.setScope(dependencyScope);
                                        if (dependencyScope == DependencyScope.IMPORT) {
                                            dependency.setType(Packaging.POM);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
        List<DependencyModel> dependencyModels = Lists.newArrayList(dependencies.values());

        Pipeline.of(dependencyModels)
                .forEach(new Predicate<DependencyModel>() {
                    @Override
                    public boolean test(DependencyModel dependencyModel) {
                        return dependencyModel.getType() == null;
                    }
                }, new Consumer<DependencyModel>() {
                    @Override
                    public void accept(DependencyModel dependencyModel) {
                        if (mavenPackageManager == null) {
                            dependencyModel.setType(Packaging.JAR);
                        } else {
                            MavenPackageArtifact artifact = mavenPackageManager.parsePom(dependencyModel);
                            if (artifact != null) {
                                Packaging packaging = artifact.getPackaging();
                                if(packaging!=null) {
                                    dependencyModel.setType(packaging);
                                    if(packaging==Packaging.POM){
                                        dependencyModel.setScope(DependencyScope.IMPORT);
                                    }
                                }
                            }

                        }
                    }
                });
        return dependencyModels;
    }

}
