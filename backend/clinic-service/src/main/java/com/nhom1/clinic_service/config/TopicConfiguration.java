package com.nhom1.clinic_service.config;

import static com.nhom1.clinic_service.kafka.TopicNames.CLINIC_DELETED;
import static com.nhom1.clinic_service.kafka.TopicNames.CLINIC_UPDATED;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {
    @Bean
    public NewTopic clinicDeletedTopic(){
        return TopicBuilder
            .name(CLINIC_DELETED.toString())
            .build();
    }

    @Bean
    public NewTopic clinicUpdatedTopic(){
        return TopicBuilder
            .name(CLINIC_UPDATED.toString())
            .build();
    }
}
