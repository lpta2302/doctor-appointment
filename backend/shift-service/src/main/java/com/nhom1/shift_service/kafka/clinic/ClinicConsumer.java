package com.nhom1.shift_service.kafka.clinic;

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
public class ClinicConsumer {

    private final ScheduleRepository scheduleRepository;

    @KafkaListener(topics = "clinic-deleted")
    @Transactional
    public void consumeClinicDeleted(ClinicInfo info){
        log.info(format("Consuming the message from clinic-deleted Topic:: %s", info.toString()));
        scheduleRepository.deleteByClinicId(info.id());
    }

    @KafkaListener(topics = "clinic-updated")
    @Transactional
    public void consumeClinicUpdated(ClinicInfo info){
        log.info(format("Consuming the message from clinic-updated Topic:: %s", info.toString()));
        scheduleRepository.updateClinicChange(info.id(), info.name());
    }
}
