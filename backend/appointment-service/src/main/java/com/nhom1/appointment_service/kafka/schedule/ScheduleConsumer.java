package com.nhom1.appointment_service.kafka.schedule;

import static java.lang.String.format;
import java.time.LocalDate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.nhom1.appointment_service.core.appointment.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleConsumer {

    private final AppointmentRepository appointmentRepository;

    @KafkaListener(topics = "schedule-deleted")
    @Transactional
    public void consumeScheduleDeleted(ScheduleInfo info){
        log.info(format("Consuming the message from schedule-deleted Topic:: %s", info.toString()));
        if (info.appliedDate().isBefore(LocalDate.now())) {
            return;
        }
        appointmentRepository.cancelBySchedule(info.clinicId(), info.appliedDate());
    }
}
