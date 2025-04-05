package com.nhom1.clinic_service.kafka.clinic;

import static com.nhom1.clinic_service.kafka.TopicNames.CLINIC_DELETED;
import static com.nhom1.clinic_service.kafka.TopicNames.CLINIC_UPDATED;
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
public class ClinicProducer {
    
    private final KafkaTemplate<String, ClinicInfo> kafkaTemplate;

    public void sendDeletedSpecializationMessage(
        ClinicInfo info){
        log.info("sending deleted clinic: " + info.toString());

        Message<ClinicInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, CLINIC_DELETED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }

    public void sendUpdatedClinicMessage(
        ClinicInfo info){
        log.info("sending updated clinic: " + info.toString());

        Message<ClinicInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, CLINIC_UPDATED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }
}
