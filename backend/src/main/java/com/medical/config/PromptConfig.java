package com.medical.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Configuration
public class PromptConfig {

    @Value("classpath:prompts.yml")
    private Resource promptsResource;

    @Bean
    @SuppressWarnings("unchecked")
    public Map<String, Object> promptTemplates() throws Exception {
        Yaml yaml = new Yaml();
        try (InputStream is = promptsResource.getInputStream()) {
            return yaml.load(is);
        }
    }
}
