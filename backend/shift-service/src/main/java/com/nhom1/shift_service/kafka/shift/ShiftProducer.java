package com.nhom1.shift_service.kafka.shift;

import static com.nhom1.shift_service.kafka.TopicNames.SHIFT_DELETED;
import static com.nhom1.shift_service.kafka.TopicNames.SHIFT_UPDATED;
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
public class ShiftProducer {
    
    private final KafkaTemplate<String, ShiftInfo> kafkaTemplate;

    public void sendDeletedShiftMessage(
        ShiftInfo info){
        log.info("sending deleted shift: " + info.toString());

        Message<ShiftInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, SHIFT_DELETED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }

    public void sendUpdatedShiftMessage(
        ShiftInfo info){
        log.info("sending updated shift: " + info.toString());

        Message<ShiftInfo> message = MessageBuilder
            .withPayload(info)
            .setHeader(KafkaHeaders.TOPIC, SHIFT_UPDATED.toString())
            .build();
        
        kafkaTemplate.send(message);
    }
}
