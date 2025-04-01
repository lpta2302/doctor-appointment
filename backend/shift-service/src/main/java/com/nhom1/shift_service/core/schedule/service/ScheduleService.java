package com.nhom1.shift_service.core.schedule.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.nhom1.shift_service.common.PageResponse;
import com.nhom1.shift_service.core.clinic.client.ClinicClient;
import com.nhom1.shift_service.core.schedule.dto.ScheduleRequest;
import com.nhom1.shift_service.core.schedule.dto.ScheduleResponse;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.schedule.entity.ScheduleId;
// import com.nhom1.shift_service.core.schedule.mapper.ScheduleMapper;
import com.nhom1.shift_service.core.schedule.repository.ScheduleRepository;
import com.nhom1.shift_service.core.schedule.specification.ScheduleSpecifications;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;
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
    private final ClinicClient clinicClient;
    
    public ScheduleId create(ScheduleRequest scheduleRequest) {
        validateSchedule(scheduleRequest);

        Schedule schedule = 
            new Schedule(scheduleRequest.appliedDate(), scheduleRequest.clinicId());

        List<Shift> shiftsOfSchedule = 
            shiftService.createShifts(scheduleRequest.shiftRequests());
        
        schedule.setShifts(shiftsOfSchedule);
        return scheduleRepository.save(schedule).getScheduleId();
    }

    public Long addShift(Long clinicId, LocalDate appliedDate, ShiftRequest shiftRequest){
        Schedule schedule = findById(clinicId, appliedDate);

        Shift newShift = shiftService.addShift(schedule, shiftRequest);
        
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

    public Schedule findById(Long clinicId, LocalDate appliedDate){
        var id = ScheduleId.builder()
            .clinicId(clinicId)
            .appliedDate(appliedDate)
            .build();

        return scheduleRepository.findById(id)
            .orElseThrow(()->new EntityNotFoundException(
                "not found schedule for clinic "+id.getClinicId()+
                "of date "+id.getAppliedDate()));
    }
    
    public ScheduleResponse findScheduleWithShiftDetailById(Long clinicId, LocalDate appliedDate){
        Schedule schedule = findById(clinicId, appliedDate);

        List<ShiftResponse> shiftResponse = shiftService.findAllBySchedule(schedule);

        ScheduleResponse response = ScheduleResponse.builder()
            .shifts(shiftResponse)
            .clinicId(clinicId)
            .appliedDate(appliedDate)
            .build();

        return response;
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

    private void validateSchedule(ScheduleRequest scheduleRequest) {
        var clinicCheckResponse = clinicClient.checkExistById(scheduleRequest.clinicId());
        if (clinicCheckResponse.getStatusCode().is4xxClientError()) {
            throw new EntityNotFoundException("not found clinic with id: "+scheduleRequest.clinicId());
        }

        if (scheduleRequest.appliedDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Can't set schedule for a day before");
        }
    }
}
