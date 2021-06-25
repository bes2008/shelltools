package com.jn.shelltools.config;

import com.jn.agileway.vfs.artifact.ArtifactProperties;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryLayout;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryProperties;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.langx.registry.GenericRegistry;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ArtifactRepositoryConfig {

    @Bean
    @ConfigurationProperties(prefix = "artifact")
    public ArtifactProperties artifactProperties(){
        return new ArtifactProperties();
    }

    @Bean("artifactRepositoryLayoutRegistry")
    public GenericRegistry<ArtifactRepositoryLayout> artifactRepositoryLayoutRegistry(){
        return new GenericRegistry<ArtifactRepositoryLayout>();
    }

    @Bean("artifactRepositoryFactory")
    public DefaultArtifactRepositoryFactory artifactRepositoryFactory(
            @Qualifier("artifactRepositoryLayoutRegistry") GenericRegistry<ArtifactRepositoryLayout> artifactRepositoryLayoutRegistry,
            ArtifactProperties artifactProperties){
        DefaultArtifactRepositoryFactory factory = new DefaultArtifactRepositoryFactory();
        factory.setArtifactRepositoryLayoutRegistry(artifactRepositoryLayoutRegistry);

        List<ArtifactRepositoryProperties> repositories = artifactProperties.getRepositories();
        Collects.forEach(repositories, new Consumer<ArtifactRepositoryProperties>() {
            @Override
            public void accept(ArtifactRepositoryProperties artifactRepositoryProperties) {
                factory.get(artifactRepositoryProperties);
            }
        });
        return factory;
    }
}
