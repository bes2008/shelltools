package com.jn.shelltools.config;

import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.net.URLs;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.net.URL;

@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {
    @Bean
    public Configuration templateConfig() throws Throwable {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        URL url = Resources.loadClassPathResource("/ftl").getUrl();
        File templateRoot = URLs.getFile(url);
        cfg.setDirectoryForTemplateLoading(templateRoot);
        cfg.setDefaultEncoding(Charsets.UTF_8.name());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }
}
