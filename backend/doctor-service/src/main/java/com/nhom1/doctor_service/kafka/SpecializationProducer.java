package com.nhom1.doctor_service.kafka;

import static com.nhom1.doctor_service.kafka.TopicNames.SPECIALIZATION_DELETED;
import static com.nhom1.doctor_service.kafka.TopicNames.SPECIALIZATION_UPDATED;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecializationProducer {
    
    private final KafkaTemplate<String, SpecializationInfo> kafkaTemplate;

    public void sendDeletedSpecializationMessage(
        SpecializationInfo info){
        log.info("sending deleted specialization: " + info.toString());

        Message<SpecializationInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, SPECIALIZATION_DELETED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }

    public void sendUpdatedSpecializationMessage(
        SpecializationInfo info){
        log.info("sending updated specialization: " + info.toString());

        Message<SpecializationInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, SPECIALIZATION_UPDATED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }
}
