package com.nhom1.shift_service.kafka.doctor;

import static java.lang.String.format;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.nhom1.shift_service.core.shift.repository.ShiftRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorConsumer {

    private final ShiftRepository shiftRepository;

    @KafkaListener(topics = "doctor-deleted")
    @Transactional
    public void consumeDoctorDeleted(DoctorInfo info){
        log.info(format("Consuming the message from doctor-deleted Topic:: %s", info));
        shiftRepository.deleteByDoctorId(info.id());
    }

    @KafkaListener(topics = "doctor-updated")
    @Transactional
    public void consumeDoctorUpdated(DoctorInfo info){
        log.info(format("Consuming the message from doctor-updated Topic:: %s", info));
        shiftRepository.updateDoctorChange(info.id(), info.code(), info.fullname(), info.phoneNumber());
    }
}
