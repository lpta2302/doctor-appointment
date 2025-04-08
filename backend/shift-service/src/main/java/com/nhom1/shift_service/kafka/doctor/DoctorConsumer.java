package com.nhom1.shift_service.kafka.doctor;

import static java.lang.String.format;
import java.util.List;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.nhom1.shift_service.core.shift.entity.Shift;
import com.nhom1.shift_service.core.shift.mapper.ShiftMapper;
import com.nhom1.shift_service.core.shift.repository.ShiftRepository;
import com.nhom1.shift_service.kafka.shift.ShiftProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorConsumer {

    private final ShiftMapper shiftMapper;

    private final ShiftProducer shiftProducer;

    private final ShiftRepository shiftRepository;

    @KafkaListener(topics = "doctor-deleted")
    @Transactional
    public void consumeDoctorDeleted(DoctorInfo info){
        log.info(format("Consuming the message from doctor-deleted Topic:: %s", info));
        List<Shift> shifts = shiftRepository.findAllByDoctorId(info.id());
        
        shiftRepository.deleteByDoctorId(info.id());
        shifts.forEach(shift->{
            shiftProducer.sendDeletedShiftMessage(
                shiftMapper.convertShiftInfoFrom(
                    shift, shift.getSchedule(), 
                    Map.of(shift.getStartTime(), shift.getEndTime()))
            );
        });
    }

    @KafkaListener(topics = "doctor-updated")
    @Transactional
    public void consumeDoctorUpdated(DoctorInfo info){
        log.info(format("Consuming the message from doctor-updated Topic:: %s", info));
        shiftRepository.updateDoctorChange(info.id(), info.code(), info.fullname(), info.phoneNumber());
    }
}
