package com.nhom1.shift_service.kafka.clinic;

import static java.lang.String.format;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.schedule.repository.ScheduleRepository;
import com.nhom1.shift_service.core.shift.repository.ShiftRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClinicConsumer {

    private final ScheduleRepository scheduleRepository;
    private final ShiftRepository shiftRepository;

    @KafkaListener(topics = "clinic-deleted")
    @Transactional
    public void consumeClinicDeleted(ClinicInfo info){
        log.info(format("Consuming the message from clinic-deleted Topic:: %s", info.toString()));
        List<Schedule> schedules = scheduleRepository.findAllByClinicId(info.id());
        schedules.forEach(schedule->{
            shiftRepository.deleteAll(schedule.getShifts());
            schedule.getShifts().clear();
        });
        scheduleRepository.deleteAll(schedules);
    }

    @KafkaListener(topics = "clinic-updated")
    @Transactional
    public void consumeClinicUpdated(ClinicInfo info){
        log.info(format("Consuming the message from clinic-updated Topic:: %s", info.toString()));
        scheduleRepository.updateClinicChange(info.id(), info.name());
    }
}
