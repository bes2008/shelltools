package com.jn.shelltools.config;

import com.jn.agileway.feign.HttpConnectionContext;
import com.jn.agileway.feign.RestServiceProvider;
import com.jn.agileway.feign.supports.rpc.rest.EasyjsonDecoder;
import com.jn.agileway.feign.supports.rpc.rest.RestStubProvider;
import com.jn.agileway.vfs.artifact.SynchronizedArtifactManager;
import com.jn.agileway.vfs.artifact.repository.DefaultArtifactRepositoryFactory;
import com.jn.easyjson.core.JSON;
import com.jn.easyjson.core.JsonException;
import com.jn.easyjson.core.factory.JsonFactorys;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer;
import com.jn.shelltools.supports.pypi.PypiPackageManager;
import com.jn.shelltools.supports.pypi.PypiPackageManagerProperties;
import com.jn.shelltools.supports.pypi.PypiPackageMetadataManager;
import com.jn.shelltools.supports.pypi.PypiRestApi;
import com.jn.shelltools.supports.pypi.repository.PypiLocalRepositoryLayout;
import com.jn.shelltools.supports.pypi.repository.PypiPackageLayout;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

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
    public RestStubProvider pipRestServiceProvider(PypiPackageManagerProperties props, HttpClient httpClient) {
        RestStubProvider provider = new RestStubProvider();
        provider.setJsonFactory(JsonFactorys.getJSONFactory());
        HttpConnectionContext context = new HttpConnectionContext();
        context.setConfiguration(props.getServer());
        context.setHttpClient(httpClient);
        provider.setDecoder(new EasyjsonDecoder(){
            @Override
            public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
                if (response != null && response.body() != null && response.status()==200) {
                    Reader reader = response.body().asReader();
                    try {
                        Object obj = ((JSON)JsonFactorys.getJSONFactory().get()).fromJson(reader, type);
                        return obj;
                    } catch (JsonException var5) {
                        throw new DecodeException(var5.getMessage(), var5);
                    }
                } else {
                    return null;
                }
            }
        });
        provider.setContext(context);
        return provider;
    }

    @Bean
    public PypiRestApi pipService(@Qualifier("pipRestServiceProvider") RestStubProvider restServiceProvider) {
        return restServiceProvider.getStub(PypiRestApi.class);
    }

    @Bean
    public PypiPackageMetadataManager pypiPackageMetadataManager(PypiRestApi pipService, @Qualifier("pipArtifactManager")
                                                                         SynchronizedArtifactManager pipArtifactManager){
        PypiPackageMetadataManager metadataManager = new PypiPackageMetadataManager();
        metadataManager.setArtifactManager(pipArtifactManager);
        metadataManager.setRestApi(pipService);
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
