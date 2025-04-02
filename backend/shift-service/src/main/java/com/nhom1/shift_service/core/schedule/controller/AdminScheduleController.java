package com.nhom1.shift_service.core.schedule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.shift_service.core.schedule.dto.ScheduleRequest;
import com.nhom1.shift_service.core.schedule.dto.ScheduleResponse;
import com.nhom1.shift_service.core.schedule.entity.ScheduleId;
import com.nhom1.shift_service.core.schedule.service.ScheduleService;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/admin/schedules")
@RequiredArgsConstructor
public class AdminScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleId> create(
        @RequestBody @Valid ScheduleRequest scheduleRequest) {

        return ResponseEntity.ok(scheduleService.create(scheduleRequest));
    }

    @PostMapping("/{clinicId}/{appliedDate}/shifts")
    public ResponseEntity<Long> addShift(
        Long clinicId,
        LocalDate appliedDate,
        @RequestBody @Valid ShiftRequest shiftRequest
    ) { 
        return ResponseEntity.ok(scheduleService.addShift(clinicId, appliedDate, shiftRequest));
    }

    @PatchMapping("/{clinicId}/{appliedDate}/shifts/{shiftId}")
    public ResponseEntity<Long> updateShift(
        Long clinicId,
        LocalDate appliedDate,
        Long shiftId,
        @RequestBody @Valid ShiftRequest shiftRequest
    ) { 
        return ResponseEntity.ok(
            scheduleService.updateShift(clinicId, appliedDate, shiftId, shiftRequest)
        );
    }

    @DeleteMapping("/{clinicId}/{appliedDate}/shifts/{shiftId}")
    public ResponseEntity<Void> removeShift(
        Long clinicId,
        LocalDate appliedDate,
        Long shiftId
    ) { 
        scheduleService.removeShift(clinicId, appliedDate, shiftId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{clinicId}/{appliedDate}")
    public ResponseEntity<ScheduleResponse> findById(
        Long clinicId,
        LocalDate appliedDate
    ) {
        return ResponseEntity.ok(scheduleService.findScheduleWithShiftDetailById(clinicId,appliedDate));
    }

    @GetMapping("/{appliedDate}/specializations/{specializationId}")
    public ResponseEntity<List<ScheduleResponse>> findBySpecialization(
        Long specializationId,
        LocalDate appliedDate
    ) {
        return ResponseEntity.ok(scheduleService.findScheduleWithShiftDetailBySpecialization(specializationId,appliedDate));
    }
    
    @DeleteMapping("/{clinicId}/{appliedDate}")
    public ResponseEntity<Void> deleteById(
        Long clinicId,
        LocalDate appliedDate
    ){
        scheduleService.deleteById(clinicId, appliedDate);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllById(
        @RequestBody List<ScheduleId> scheduleIds
    ){
        scheduleService.deleteAllById(scheduleIds);

        return ResponseEntity.noContent().build();
    }
}
