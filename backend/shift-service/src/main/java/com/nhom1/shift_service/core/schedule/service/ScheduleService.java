package com.nhom1.shift_service.core.schedule.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nhom1.shift_service.core.clinic.client.ClinicClient;
import com.nhom1.shift_service.core.clinic.dto.ClinicResponse;
import com.nhom1.shift_service.core.schedule.dto.ScheduleRequest;
import com.nhom1.shift_service.core.schedule.dto.ScheduleResponse;
import com.nhom1.shift_service.core.schedule.dto.ScheduleTimeResponse;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.schedule.entity.ScheduleId;
import com.nhom1.shift_service.core.schedule.mapper.ScheduleMapper;
import com.nhom1.shift_service.core.schedule.repository.ScheduleRepository;
import com.nhom1.shift_service.core.schedule.specification.ScheduleSpecifications;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;
import com.nhom1.shift_service.core.shift.entity.Shift;
import com.nhom1.shift_service.core.shift.service.ShiftService;
import com.nhom1.shift_service.kafka.schedule.ScheduleInfo;
import com.nhom1.shift_service.kafka.schedule.ScheduleProducer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleProducer scheduleProducer;

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ShiftService shiftService;
    private final ClinicClient clinicClient;
    
    public ScheduleId create(ScheduleRequest scheduleRequest) {
        ClinicResponse clinicResponse = 
            clinicClient.findById(scheduleRequest.clinicId())
            .orElseThrow(()->new EntityNotFoundException("not found clinic with id: "+
                scheduleRequest.clinicId()));

        Schedule schedule = 
            new Schedule(scheduleRequest.appliedDate(), scheduleRequest.clinicId());

        schedule.setSpecializationId(clinicResponse.specializationId());
        schedule.setClinicName(clinicResponse.name());
        schedule.setSpecializationName(clinicResponse.specializationName());

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
                shift.getId().equals(shiftId))
            .findFirst()
            .orElseThrow(()-> new EntityNotFoundException("not found shift with id: "+shiftId));
        
        shiftService.update(schedule, updatingShift, shiftRequest);

        scheduleRepository.save(schedule);
        scheduleRepository.flush();
        return updatingShift.getId();
    }
    
    public ScheduleId removeShift(Long clinicId, LocalDate appliedDate, Long shiftId){
        Schedule schedule = findById(clinicId, appliedDate);

        Shift removingShift = schedule.getShifts().stream().filter(shift->shift.getId().equals(shiftId)).findFirst()
            .orElseThrow(()->new EntityNotFoundException("not found shift"));

        shiftService.removeShift(removingShift);

        schedule.getShifts().remove(removingShift);
        ScheduleId id = scheduleRepository.save(schedule).getScheduleId();

        return id;
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

        // List<ShiftResponse> shiftResponse = shiftService.findAllBySchedule(schedule);
        List<ShiftResponse> shiftResponse = shiftService.convertToShiftResponses(schedule.getShifts());

        ScheduleResponse response = scheduleMapper.convertScheduleResponseFrom(schedule, shiftResponse);

        return response;
    }

    public List<ScheduleResponse> findScheduleWithShiftDetailBySpecialization(Long specializationId, LocalDate appliedDate){
        Specification<Schedule> specification = ScheduleSpecifications.haveAppliedDate(appliedDate)
            .and(ScheduleSpecifications.haveSpecializationIdEqual(specializationId));

        List<Schedule> schedules = scheduleRepository.findAll(specification);
        Map<Long, List<ShiftResponse>> shiftResponses = new HashMap<>();
        schedules.forEach(schedule->{
            shiftResponses.put(
                schedule.getScheduleId().getClinicId(), 
                shiftService.convertToShiftResponses(schedule.getShifts())
            );
        });

        List<ScheduleResponse> responses = schedules.stream().map(
            schedule->
                scheduleMapper.convertScheduleResponseFrom(schedule, shiftResponses.get(schedule.getScheduleId().getClinicId()))
        ).toList();

        return responses;
    }
    
    @Transactional
    public void deleteById(Long clinicId, LocalDate appliedDate){
        scheduleRepository.deleteByClinicIdAndAppliedDate(clinicId, appliedDate);

        scheduleProducer.sendDeletedScheduleMessage(
            ScheduleInfo.builder().appliedDate(appliedDate).clinicId(clinicId).build()
        );
    }

    public void deleteAllById(List<ScheduleId> scheduleIds){
        scheduleRepository.deleteAllById(scheduleIds);
    }

    public ScheduleTimeResponse findScheduleTimeById(Long clinicId, LocalDate appliedDate) {
        Schedule schedule = findById(clinicId, appliedDate);

        Map<LocalTime, LocalTime> timeMap = schedule.getShifts().stream().collect(
            Collectors.toMap(Shift::getStartTime, Shift::getEndTime)
        );

        return ScheduleTimeResponse.builder()
            .clinicName(schedule.getClinicName())
            .specializationId(schedule.getSpecializationId())
            .specializationName(schedule.getSpecializationName())
            .shiftsTime(timeMap)
            .build();

    }
}
