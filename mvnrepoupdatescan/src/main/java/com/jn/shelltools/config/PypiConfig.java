package com.jn.shelltools.config;

import com.jn.agileway.feign.HttpConnectionContext;
import com.jn.agileway.feign.RestServiceProvider;
import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.easyjson.core.factory.JsonFactorys;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.shelltools.core.pypi.*;
import com.jn.shelltools.core.pypi.repository.PypiLocalRepositoryLayout;
import com.jn.shelltools.core.pypi.repository.PypiPackageLayout;
import org.apache.commons.vfs2.FileSystemManager;
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
    public PypiLocalRepositoryLayout pypiLocalRepositoryLayout(){
        return new PypiLocalRepositoryLayout();
    }

    @Bean
    public PypiPackageLayout pypiPackageLayout() {
        return new PypiPackageLayout();
    }

    @Bean(name = "pipArtifactManager")
    public SynchronizedArtifactManager pipArtifactManager(
            DefaultArtifactRepositoryFactory factory,
            PypiPackageManagerProperties pipPackageManagerProperties,
            FileSystemManager fileSystemManager) {
        SynchronizedArtifactManager artifactManager = new SynchronizedArtifactManager();
        Collects.forEach(pipPackageManagerProperties.getSources(), new Consumer<String>() {
            @Override
            public void accept(String source) {
                artifactManager.addSource(factory.get(source));
            }
        });
        artifactManager.setDestination(factory.get(pipPackageManagerProperties.getDestination()));
        artifactManager.setFileSystemManager(fileSystemManager);
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
    public PypiPackageMetadataManager pypiPackageMetadataManager(PypiService pipService,@Qualifier("pipArtifactManager")
                                                                         SynchronizedArtifactManager pipArtifactManager){
        PypiPackageMetadataManager metadataManager = new PypiPackageMetadataManager();
        metadataManager.setArtifactManager(pipArtifactManager);
        metadataManager.setService(pipService);
        return metadataManager;

    }

    @Bean
    public PypiPackageManager pipPackageManager(
            PypiPackageMetadataManager pypiPackageMetadataManager,
            @Qualifier("pipArtifactManager")
            SynchronizedArtifactManager pipArtifactManager
            ) {
        PypiPackageManager manager = new PypiPackageManager();
        manager.setArtifactManager(pipArtifactManager);
        manager.setMetadataManager(pypiPackageMetadataManager);
        return manager;
    }
}
