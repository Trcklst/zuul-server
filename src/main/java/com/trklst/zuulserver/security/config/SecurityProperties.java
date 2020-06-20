package com.trklst.zuulserver.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private List<String> excludedPatterns = new ArrayList<>();

    public List<String> getExcludedPatterns() {
        return excludedPatterns;
    }

    public void setExcludedPatterns(List<String> excludedPatterns) {
        this.excludedPatterns = excludedPatterns;
    }
}
