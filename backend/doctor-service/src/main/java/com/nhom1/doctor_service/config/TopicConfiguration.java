package com.nhom1.doctor_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.nhom1.doctor_service.kafka.TopicNames;

@Configuration
public class TopicConfiguration {
    @Bean
    NewTopic specializationDeletedTopic(){
        return TopicBuilder
            .name(TopicNames.SPECIALIZATION_DELETED.toString())
            .build();
    }
    @Bean
    NewTopic specializationUpdatedTopic(){
        return TopicBuilder
            .name(TopicNames.SPECIALIZATION_UPDATED.toString())
            .build();
    }

    @Bean
    NewTopic doctorDeletedTopic(){
        return TopicBuilder
            .name(TopicNames.DOCTOR_DELETED.toString())
            .build();
    }

    @Bean
    NewTopic doctorUpdatedTopic(){
        return TopicBuilder
            .name(TopicNames.DOCTOR_UPDATED.toString())
            .build();
    }
}
