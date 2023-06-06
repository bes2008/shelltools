package com.jn.shelltools.config;

import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.shelltools.supports.maven.MavenPackageManagerProperties;
import org.apache.commons.vfs2.FileSystemManager;
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

    @Bean(name = "mavenArtifactManager")
    public SynchronizedArtifactManager mavenArtifactManager(
            DefaultArtifactRepositoryFactory factory,
            MavenPackageManagerProperties mavenPackageManagerProperties,
            FileSystemManager fileSystemManager) {
        SynchronizedArtifactManager artifactManager = new SynchronizedArtifactManager();
        Collects.forEach(mavenPackageManagerProperties.getSources(), new Consumer<String>() {
            @Override
            public void accept(String source) {
                artifactManager.addSource(factory.get(source));
            }
        });
        artifactManager.setDestination(factory.get(mavenPackageManagerProperties.getDestination()));
        artifactManager.setFileSystemManager(fileSystemManager);
        return artifactManager;
    }


}
