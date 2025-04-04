package com.nhom1.doctor_service.kafka;

import static com.nhom1.doctor_service.kafka.TopicNames.DOCTOR_DELETED;
import static com.nhom1.doctor_service.kafka.TopicNames.DOCTOR_UPDATED;
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
public class DoctorProducer {
    
    private final KafkaTemplate<String, DoctorInfo> kafkaTemplate;

    public void sendDeletedDoctorMessage(
        DoctorInfo info){
        log.info("sending deleted doctor: " + info.toString());

        Message<DoctorInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, DOCTOR_DELETED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }

    public void sendUpdatedDoctorMessage(
        DoctorInfo info){
        log.info("sending updated doctor: " + info.toString());

        Message<DoctorInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, DOCTOR_UPDATED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }
}
