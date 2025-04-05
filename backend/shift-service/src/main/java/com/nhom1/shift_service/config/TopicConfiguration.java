package com.nhom1.shift_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.nhom1.shift_service.kafka.TopicNames;

@Configuration
public class TopicConfiguration {
    @Bean
    public NewTopic shiftDeletedTopic(){
        return TopicBuilder
            .name(TopicNames.SHIFT_DELETED.toString())
            .build();
    }
    @Bean
    public NewTopic shiftUpdatedTopic(){
        return TopicBuilder
            .name(TopicNames.SHIFT_UPDATED.toString())
            .build();
    }

    @Bean
    public NewTopic scheduleDeletedTopic(){
        return TopicBuilder
            .name(TopicNames.SCHEDULE_DELETED.toString())
            .build();
    }
}
