package com.jn.shelltools.config;

import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepository;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.shelltools.supports.maven.Maven2LocalRepositoryLayout;
import com.jn.shelltools.supports.maven.MavenPackageManager;
import com.jn.shelltools.supports.maven.MavenPackageManagerProperties;
import com.jn.shelltools.supports.maven.dependencies.MavenDependenciesTreeParser;
import org.apache.commons.vfs2.FileSystemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MavenConfig {

    @Bean
    @ConfigurationProperties(prefix = "maven")
    public MavenPackageManagerProperties mavenPackageManagerProperties() {
        return new MavenPackageManagerProperties();
    }

    @Bean("m2local")
    public Maven2LocalRepositoryLayout maven2LocalRepositoryLayout(){
        return new Maven2LocalRepositoryLayout();
    }

    @Bean(name = "mavenArtifactManager")
    public SynchronizedArtifactManager mavenArtifactManager(
            DefaultArtifactRepositoryFactory factory,
            MavenPackageManagerProperties mavenPackageManagerProperties,
            FileSystemManager fileSystemManager) {
        SynchronizedArtifactManager artifactManager = new SynchronizedArtifactManager();
        Collects.forEach(mavenPackageManagerProperties.getSources(), new Consumer<String>() {
            @Override
            public void accept(String source) {
                ArtifactRepository repository = factory.get(source);
                if (repository != null) {
                    artifactManager.addSource(repository);
                }
            }
        });
        artifactManager.setDestination(factory.get(mavenPackageManagerProperties.getDestination()));
        artifactManager.setFileSystemManager(fileSystemManager);
        return artifactManager;
    }

    @Bean
    @Autowired
    public MavenPackageManager mavenPackageManager(
            @Qualifier("mavenArtifactManager")
                    ArtifactManager mavenArtifactManager) {
        MavenPackageManager packageManager = new MavenPackageManager();
        packageManager.setArtifactManager(mavenArtifactManager);
        return packageManager;
    }

    @Bean
    public MavenDependenciesTreeParser treeStyleDependenciesParser(MavenPackageManager mavenPackageManager) {
        MavenDependenciesTreeParser p = new MavenDependenciesTreeParser();
        p.setMavenPackageManager(mavenPackageManager);
        return p;
    }


}
