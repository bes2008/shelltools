package com.jn.shelltools.config;

import com.jn.agileway.vfs.artifact.ArtifactProperties;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryLayout;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryProperties;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.agileway.vfs.artifact.repository.LocalArtifactRepositoryLayout;
import com.jn.langx.registry.GenericRegistry;
import com.jn.langx.util.Objs;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ArtifactRepositoryConfig {

    @Bean(name = "artifactProperties")
    @ConfigurationProperties(prefix = "artifact")
    public ArtifactProperties artifactProperties() {
        return new ArtifactProperties();
    }

    @ConditionalOnMissingBean(name = "artifactRepositoryLayoutRegistry")
    @Bean("artifactRepositoryLayoutRegistry")
    public GenericRegistry<ArtifactRepositoryLayout> artifactRepositoryLayoutRegistry() {
        return new GenericRegistry<ArtifactRepositoryLayout>();
    }

    @ConditionalOnMissingBean(name = "localArtifactRepositoryLayout")
    @Bean(name = "localArtifactRepositoryLayout")
    public LocalArtifactRepositoryLayout localArtifactRepositoryLayout() {
        return new LocalArtifactRepositoryLayout();
    }

    @ConditionalOnMissingBean(name = "artifactRepositoryFactory")
    @Bean(name = "artifactRepositoryFactory")
    public DefaultArtifactRepositoryFactory artifactRepositoryFactory(
            @Qualifier("artifactRepositoryLayoutRegistry")
                    GenericRegistry<ArtifactRepositoryLayout> layoutRegistry,
            ArtifactProperties artifactProperties,
            ObjectProvider<List<ArtifactRepositoryLayout>> layoutObjectProvider) {

        List<ArtifactRepositoryLayout> layouts = layoutObjectProvider.getObject();
        if (Objs.isNotEmpty(layouts)) {
            Collects.forEach(layouts, new Consumer<ArtifactRepositoryLayout>() {
                @Override
                public void accept(ArtifactRepositoryLayout layout) {
                    layoutRegistry.register(layout);
                }
            });
        }

        DefaultArtifactRepositoryFactory factory = new DefaultArtifactRepositoryFactory();
        factory.setArtifactRepositoryLayoutRegistry(layoutRegistry);


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
