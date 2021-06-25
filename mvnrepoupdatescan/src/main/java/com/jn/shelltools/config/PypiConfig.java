package com.jn.shelltools.config;

import com.jn.agileway.feign.HttpConnectionContext;
import com.jn.agileway.feign.RestServiceProvider;
import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.agileway.vfs.artifact.repository.ArtifactRepositoryLayout;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.easyjson.core.factory.JsonFactorys;
import com.jn.langx.registry.GenericRegistry;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.shelltools.core.pypi.PypiPackageLayout;
import com.jn.shelltools.core.pypi.PypiPackageManager;
import com.jn.shelltools.core.pypi.PypiService;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PypiConfig {

    @Bean
    @ConfigurationProperties(prefix = "pypi")
    public PypiPackageManagerProperties pipPackageManagerProperties() {
        return new PypiPackageManagerProperties();
    }

    @Bean
    public PypiPackageLayout pypiPackageLayout(@Qualifier("artifactRepositoryLayoutRegistry") GenericRegistry<ArtifactRepositoryLayout> artifactRepositoryLayoutRegistry) {
        return new PypiPackageLayout();
    }

    @Bean
    public SynchronizedArtifactManager pipArtifactManager(
            DefaultArtifactRepositoryFactory factory,
            PypiPackageManagerProperties pipPackageManagerProperties) {
        SynchronizedArtifactManager artifactManager = new SynchronizedArtifactManager();
        Collects.forEach(pipPackageManagerProperties.getSources(), new Consumer<String>() {
            @Override
            public void accept(String source) {
                artifactManager.addSource(factory.get(source));
            }
        });
        artifactManager.setDestination(factory.get(pipPackageManagerProperties.getDestination()));
        return artifactManager;
    }

    @Bean("pipRestServiceProvider")
    public RestServiceProvider pipRestServiceProvider(PypiPackageManagerProperties props, HttpClient httpClient) {
        RestServiceProvider provider = new RestServiceProvider();
        provider.setJsonFactory(JsonFactorys.getJSONFactory());
        HttpConnectionContext context = new HttpConnectionContext();
        context.setConfiguration(props.getServer());
        context.setHttpClient(httpClient);
        provider.setContext(context);
        return provider;
    }

    @Bean
    public PypiService pipService(@Qualifier("pipRestServiceProvider") RestServiceProvider restServiceProvider) {
        return restServiceProvider.getService(PypiService.class);
    }

    @Bean
    public PypiPackageManager pipPackageManager(PypiService pipService) {
        PypiPackageManager manager = new PypiPackageManager();
        manager.setService(pipService);
        return manager;
    }
}
