package com.nhom1.shift_service.kafka.schedule;

import static com.nhom1.shift_service.kafka.TopicNames.SCHEDULE_DELETED;
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
public class ScheduleProducer {
    
    private final KafkaTemplate<String, ScheduleInfo> kafkaTemplate;

    public void sendDeletedScheduleMessage(
        ScheduleInfo info){
        log.info("sending deleted schedule: " + info.toString());

        Message<ScheduleInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, SCHEDULE_DELETED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }
}
