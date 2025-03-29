package com.nhom1.shift_service.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi adminGroup(){
        return GroupedOpenApi.builder()
            .group("admin api")
            .pathsToMatch("/admin/**")
            .build();
    }

    @Bean
    public GroupedOpenApi customerGroup(){
        return GroupedOpenApi.builder()
            .group("customer api")
            .pathsToExclude("/admin/**")
            .pathsToExclude("/server/**")
            .build();
    }
}
