package com.jn.shelltools.config;

import com.jn.agileway.feign.HttpConnectionContext;
import com.jn.agileway.feign.HttpConnectionProperties;
import com.jn.agileway.feign.RestServiceProvider;
import com.jn.easyjson.core.factory.JsonFactorys;
import com.jn.shelltools.core.pypi.PipPackageManager;
import com.jn.shelltools.core.pypi.PipService;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipConfig {

    @Bean
    @ConfigurationProperties(prefix = "pip.server")
    public HttpConnectionProperties pipServerProperties(){
        return new HttpConnectionProperties();
    }

    @Bean("pipRestServiceProvider")
    public RestServiceProvider pipRestServiceProvider(HttpConnectionProperties props, HttpClient httpClient) {
        RestServiceProvider provider = new RestServiceProvider();
        provider.setJsonFactory(JsonFactorys.getJSONFactory());
        HttpConnectionContext context = new HttpConnectionContext();
        context.setConfiguration(props);
        context.setHttpClient(httpClient);
        provider.setContext(context);
        return provider;
    }

    @Bean
    public PipService pipService(@Qualifier("pipRestServiceProvider") RestServiceProvider restServiceProvider){
        return restServiceProvider.getService(PipService.class);
    }

    @Bean
    public PipPackageManager pipPackageManager(PipService pipService){
        PipPackageManager manager = new PipPackageManager();
        manager.setService(pipService);
        return manager;
    }
}
