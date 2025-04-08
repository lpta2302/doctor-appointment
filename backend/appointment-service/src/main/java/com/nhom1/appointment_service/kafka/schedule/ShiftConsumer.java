package com.nhom1.appointment_service.kafka.schedule;

import static java.lang.String.format;
import org.apache.commons.lang.IllegalClassException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.nhom1.appointment_service.core.appointment.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShiftConsumer {

    private final AppointmentRepository appointmentRepository;

    @KafkaListener(topics = "shift-deleted")
    @Transactional
    public void consumeShiftDeleted(ShiftInfo info){
        log.info(format("Consuming the message from shift-deleted Topic:: %s", info.toString()));
        if (info.shiftTime().isEmpty()) {
            throw new IllegalClassException("not valid shift time");
        }
        var entry = info.shiftTime().entrySet().iterator().next();
        var oldStartTime = entry.getKey();
        var oldEndTime = entry.getValue();

        appointmentRepository.cancelByShiftTime(oldStartTime, oldEndTime, info.appliedDate(), info.clinicId());
    }

    @KafkaListener(topics = "shift-updated")
    @Transactional
    public void consumeShiftUpdated(ShiftInfo info){
        log.info(format("Consuming the message from shift-updated Topic:: %s", info.toString()));
        if (info.shiftTime().size() < 2) {
            throw new IllegalClassException("not valid shift time");
        }
        var iterator = info.shiftTime().entrySet().iterator();

        var entry = iterator.next();
        var oldStartTime = entry.getKey();
        var oldEndTime = entry.getValue();

        var newEntry = iterator.next();
        var newStartTime = newEntry.getKey();
        var newEndTime = newEntry.getValue();
        
        appointmentRepository.cancelAllByNewShiftTime(
            oldStartTime, oldEndTime,
            newStartTime, newEndTime,
            info.appliedDate(), info.clinicId());

    }
}
