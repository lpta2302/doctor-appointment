package com.nhom1.shift_service.core.schedule.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.shift_service.core.schedule.dto.ScheduleResponse;
import com.nhom1.shift_service.core.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{clinicId}/{appliedDate}")
    public ResponseEntity<ScheduleResponse> findById(
        @PathVariable Long clinicId,
        @PathVariable LocalDate appliedDate
    ) {
        return ResponseEntity.ok(scheduleService.findScheduleWithShiftDetailById(clinicId,appliedDate));
    }

    @GetMapping("/{appliedDate}/specializations/{specializationId}")
    public ResponseEntity<List<ScheduleResponse>> findBySpecialization(
        @PathVariable Long specializationId,
        @PathVariable LocalDate appliedDate
    ) {
        return ResponseEntity.ok(scheduleService.findScheduleWithShiftDetailBySpecialization(specializationId,appliedDate));
    }
    
    
}
