package com.nhom1.doctor_service.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Value("${server.servlet.context-path}")
    private String BASE_URL;
    @Bean
    public GroupedOpenApi adminGroup(){
        return GroupedOpenApi.builder()
            .group("admin api")
            .pathsToMatch(BASE_URL+"/admin/**")
            .build();
    }

    @Bean
    public GroupedOpenApi customerGroup(){
        return GroupedOpenApi.builder()
            .group("customer api")
            .packagesToExclude(BASE_URL+"/admin/**")
            .build();
    }
}
