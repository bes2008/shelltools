package com.jn.shelltools.supports.maven.dependencies;

import com.jn.langx.Parser;
import com.jn.langx.annotation.Nullable;
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
import com.jn.langx.util.logging.Loggers;
import com.jn.langx.util.regexp.Regexp;
import com.jn.langx.util.regexp.Regexps;
import com.jn.shelltools.supports.maven.MavenPackageManager;
import com.jn.shelltools.supports.maven.pom.MavenPomParser;
import com.jn.shelltools.supports.maven.model.Dependency;
import com.jn.shelltools.supports.maven.model.DependencyScope;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import com.jn.shelltools.supports.maven.model.Packaging;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MavenDependenciesTreeParser implements Parser<Resource, List<Dependency>> {
    public static final Regexp dependencyExpr = Regexps.compile("(?:((\\|)?\\s+)*?[+\\\\]-+)?(\\s+)?(?<groupId>[^:'\"\\(* \\t]+)\\:(?<artifactId>[^:'\"\\(* \\t]+)\\:(?<version>[^:'\"\\(* \\t]+)(\\:(?<scope>\\w+))?(\\s+.*)?");
    private static final Logger logger = Loggers.getLogger(MavenDependenciesTreeParser.class);
    @Nullable
    private MavenPackageManager mavenPackageManager;
    private MavenPomParser.Builder builder = new MavenPomParser.Builder().parsePackaging(true);

    public void setMavenPackageManager(MavenPackageManager mavenPackageManager) {
        this.mavenPackageManager = mavenPackageManager;
    }

    public List<Dependency> parse(Resource resource) {
        Map<String, Dependency> dependencies = new LinkedHashMap<>();
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
                                Dependency dependency = new Dependency(groupId, artifactId, version);
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
        List<Dependency> dependencyModels = Lists.newArrayList(dependencies.values());

        Pipeline.of(dependencyModels)
                .forEach(new Predicate<Dependency>() {
                    @Override
                    public boolean test(Dependency dependencyModel) {
                        return dependencyModel.getType() == null;
                    }
                }, new Consumer<Dependency>() {
                    @Override
                    public void accept(Dependency dependencyModel) {
                        if (mavenPackageManager == null) {
                            dependencyModel.setType(Packaging.JAR);
                        } else {
                            try {
                                MavenPackageArtifact artifact = mavenPackageManager.parsePom(builder, dependencyModel);
                                if (artifact != null) {
                                    Packaging packaging = artifact.getPackaging();
                                    if (packaging != null) {
                                        dependencyModel.setType(packaging);
                                        if (packaging == Packaging.POM) {
                                            dependencyModel.setScope(DependencyScope.IMPORT);
                                        }
                                    } else {
                                        dependencyModel.setType(Packaging.JAR);
                                    }
                                }
                            } catch (Throwable ex) {
                                logger.error("error when parse pom for : {}", dependencyModel.toString());
                                if (Objs.isNull(dependencyModel.getType())) {
                                    dependencyModel.setType(Packaging.JAR);
                                }
                            }
                        }
                    }
                });
        return dependencyModels;
    }

}
