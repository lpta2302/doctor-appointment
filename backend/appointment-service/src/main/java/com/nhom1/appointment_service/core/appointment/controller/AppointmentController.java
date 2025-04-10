package com.nhom1.appointment_service.core.appointment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.appointment_service.core.appointment.dto.AppointmentRequest;
import com.nhom1.appointment_service.core.appointment.dto.AvailableTime;
import com.nhom1.appointment_service.core.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Long> create(
        @RequestBody @Valid AppointmentRequest request
    ) {
        return ResponseEntity.ok(appointmentService.create(request));
    }

    @GetMapping("/{clinicId}/{appointmentDate}/time")
    public ResponseEntity<List<AvailableTime>> findAllAvailableTime(
            @PathVariable Long clinicId, 
            @PathVariable LocalDate appointmentDate) {
        return ResponseEntity.ok(
            appointmentService.findAllAvailableTime(clinicId, appointmentDate)
        );
    }
    
    
}
