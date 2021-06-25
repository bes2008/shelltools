package com.jn.shelltools.config;

import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryLayout;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.langx.registry.GenericRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtifactRepositoryConfig {

    @Bean("artifactRepositoryLayoutRegistry")
    public GenericRegistry<ArtifactRepositoryLayout> artifactRepositoryLayoutRegistry(){
        return new GenericRegistry<ArtifactRepositoryLayout>();
    }

    @Bean("artifactRepositoryFactory")
    public DefaultArtifactRepositoryFactory artifactRepositoryFactory(@Qualifier("artifactRepositoryLayoutRegistry") GenericRegistry<ArtifactRepositoryLayout> artifactRepositoryLayoutRegistry){
        DefaultArtifactRepositoryFactory factory = new DefaultArtifactRepositoryFactory();
        factory.setArtifactRepositoryLayoutRegistry(artifactRepositoryLayoutRegistry);
        return factory;
    }

}
