package com.trklst.zuulserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private static final String AUTHENTICATION_PATTERN = "/oauth";
    private static final String REGISTER_PATTERN = "/api/register";

    @Bean
    public List<String> patternsWithoutSecurity() {
        return Arrays.asList(
                AUTHENTICATION_PATTERN,
                REGISTER_PATTERN
        );
    }
}
