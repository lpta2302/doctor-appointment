package com.nhom1.appointment_service.core.patient.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.appointment_service.common.PageResponse;
import com.nhom1.appointment_service.core.patient.dto.PatientResponse;
import com.nhom1.appointment_service.core.patient.service.PatientService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{phoneNumber}/{fullname}")
    public ResponseEntity<PatientResponse> findById(
        @PathVariable
        @Pattern(regexp = "^(0)[0-9]{9}$", message = "Invalid phone number format")
        String phoneNumber,
        @PathVariable
        String fullname
        ) {
        return ResponseEntity.ok(patientService.findPatientDetailByPhoneNumberAndFullname(phoneNumber, fullname));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<PatientResponse>> search(
        @RequestParam(required = false)
        @Pattern(regexp = "^(0)[0-9]{9}$", message = "Invalid phone number format")
        String phoneNumber,
        @RequestParam(required = false)
        String fullname,
        @ParameterObject
        @PageableDefault(size = 15, page = 0)
        Pageable pageable
        ) {
        return ResponseEntity.ok(patientService.search(phoneNumber, fullname, pageable));
    }
    
}
