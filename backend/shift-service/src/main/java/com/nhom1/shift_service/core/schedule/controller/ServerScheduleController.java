package com.nhom1.shift_service.core.schedule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.shift_service.core.schedule.dto.ScheduleTimeResponse;
import com.nhom1.shift_service.core.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/server/schedules")
@RequiredArgsConstructor
public class ServerScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{clinicId}/{appliedDate}/time")
    public ResponseEntity<ScheduleTimeResponse> findScheduleTimeById(
        @PathVariable Long clinicId, 
        @PathVariable LocalDate appliedDate) {
        return ResponseEntity.ok(scheduleService.findScheduleTimeById(clinicId, appliedDate));
    }
    
}
