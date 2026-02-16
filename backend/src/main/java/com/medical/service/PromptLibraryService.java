package com.medical.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromptLibraryService {

    private final Map<String, Object> promptTemplates;

    @SuppressWarnings("unchecked")
    public String getTemplate(String templateKey) {
        Map<String, Object> prompts = (Map<String, Object>) promptTemplates.get("prompts");
        if (prompts == null) {
            throw new RuntimeException("No prompts section found in prompts.yml");
        }
        Map<String, Object> templateEntry = (Map<String, Object>) prompts.get(templateKey);
        if (templateEntry == null) {
            throw new RuntimeException("Template not found: " + templateKey);
        }
        String template = (String) templateEntry.get("template");
        log.debug("Loaded template '{}': {} chars", templateKey, template.length());
        return template;
    }

    public String resolveTemplate(String templateKey, Map<String, String> params) {
        String template = getTemplate(templateKey);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        log.debug("Resolved template '{}' with {} parameters", templateKey, params.size());
        return template;
    }
}
