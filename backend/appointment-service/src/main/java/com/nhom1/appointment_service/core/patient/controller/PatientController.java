package com.nhom1.appointment_service.core.patient.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import com.nhom1.appointment_service.core.patient.service.PatientService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Patient> findByPhoneNumber(
        @PathVariable
        @Pattern(regexp = "^(0)[0-9]{9}$", message = "Invalid phone number format")
        String phoneNumber) {
        return ResponseEntity.ok(patientService.findByPhoneNumber(phoneNumber));
    }
    
}
