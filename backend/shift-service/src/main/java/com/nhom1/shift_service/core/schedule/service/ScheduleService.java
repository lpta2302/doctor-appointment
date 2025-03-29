package com.nhom1.shift_service.core.schedule.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.nhom1.shift_service.common.PageResponse;
import com.nhom1.shift_service.core.schedule.dto.ScheduleRequest;
import com.nhom1.shift_service.core.schedule.dto.ScheduleResponse;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.schedule.entity.ScheduleId;
import com.nhom1.shift_service.core.schedule.mapper.ScheduleMapper;
// import com.nhom1.shift_service.core.schedule.mapper.ScheduleMapper;
import com.nhom1.shift_service.core.schedule.repository.ScheduleRepository;
import com.nhom1.shift_service.core.schedule.specification.ScheduleSpecifications;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import com.nhom1.shift_service.core.shift.entity.Shift;
import com.nhom1.shift_service.core.shift.service.ShiftService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    // private final ScheduleMapper scheduleMapper;
    private final ShiftService shiftService;
    private final ScheduleMapper scheduleMapper;
    
    public ScheduleId create(ScheduleRequest scheduleRequest) {
        Schedule schedule = 
            new Schedule(scheduleRequest.appliedDate(), scheduleRequest.clinicId());

        List<Shift> shiftsOfSchedule = 
            shiftService.createShifts(scheduleRequest.shiftRequests());
        
        schedule.setShifts(shiftsOfSchedule);
        return scheduleRepository.save(schedule).getScheduleId();
    }

    public Long addShift(Long clinicId, LocalDate appliedDate, ShiftRequest shiftRequest){
        Schedule schedule = findById(clinicId, appliedDate);

        Shift newShift = 
            Shift.builder()
                .doctorId(shiftRequest.doctorId())
                .specializationId(shiftRequest.specializationId())
                .startTime(shiftRequest.startTime())
                .endTime(shiftRequest.endTime())
                .build();

        shiftService.addShift(schedule, newShift);
        
        scheduleRepository.save(schedule);
        scheduleRepository.flush();
        return newShift.getId();
    }

    public Long updateShift(Long clinicId, LocalDate appliedDate, Long shiftId, ShiftRequest shiftRequest){
        Schedule schedule = findById(clinicId, appliedDate);

        Shift updatingShift = schedule.getShifts().stream()
            .filter(shift-> 
                shift.getId() == shiftId)
            .findFirst()
            .orElseThrow(()-> new EntityNotFoundException("not found shift with id: "+shiftId));
        
        shiftService.update(schedule, updatingShift, shiftRequest);

        scheduleRepository.save(schedule);
        scheduleRepository.flush();
        return updatingShift.getId();
    }
    
    public ScheduleId removeShift(Long clinicId, LocalDate appliedDate, Long shiftId){
        Schedule schedule = findById(clinicId, appliedDate);

        schedule.getShifts().removeIf(shift -> shift.getId() == shiftId);

        return scheduleRepository.save(schedule).getScheduleId();
    }

    public Schedule findById(ScheduleId id){
        return scheduleRepository.findById(id)
        .orElseThrow(()->new EntityNotFoundException(
            "not found schedule for clinic "+id.getClinicId()+
            "of date "+id.getAppliedDate()));
    }

    public Schedule findById(Long clinicId, LocalDate appliedDate){
        var id = ScheduleId.builder()
            .clinicId(clinicId)
            .appliedDate(appliedDate)
            .build();

        return findById(id);
    }
    
    public ScheduleResponse findScheduleDetailById(Long clinicId, LocalDate appliedDate){
        var id = ScheduleId.builder()
            .clinicId(clinicId)
            .appliedDate(appliedDate)
            .build();

        return null;
    }
        
    public PageResponse<Schedule> search(Map<String, String> params, Pageable pageable){
        Specification<Schedule> specification = ScheduleSpecifications.createSearchSpecification(params);
        Page<Schedule> pageResult = scheduleRepository.findAll(specification, pageable);
        
        return PageResponse.fromPage(pageResult);
    }
        
    public void deleteById(Long clinicId, LocalDate appliedDate){
        scheduleRepository.deleteById(ScheduleId.builder()
            .clinicId(clinicId)
            .appliedDate(appliedDate)
            .build());
    }

    public void deleteAllById(List<ScheduleId> scheduleIds){
        scheduleRepository.deleteAllById(scheduleIds);
    }
}
