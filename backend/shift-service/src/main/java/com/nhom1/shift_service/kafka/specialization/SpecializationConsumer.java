package com.nhom1.shift_service.kafka.specialization;

import static java.lang.String.format;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.nhom1.shift_service.core.schedule.repository.ScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecializationConsumer {

    private final ScheduleRepository scheduleRepository;

    @KafkaListener(topics = "specialization-deleted")
    @Transactional
    public void consumeSpecializationDeleted(SpecializationInfo info){
        log.info(format("Consuming the message from specialization-deleted Topic:: %s", info.toString()));
        scheduleRepository.deleteSpecializationDetailOfSchedules(info.id());
    }

    @KafkaListener(topics = "specialization-updated")
    @Transactional
    public void consumeSpecializationUpdated(SpecializationInfo info){
        log.info(format("Consuming the message from specialization-updated Topic:: %s", info.toString()));
        scheduleRepository.updateSpecializationDetailOfSchedules(info.id(), info.name());
    }
}
