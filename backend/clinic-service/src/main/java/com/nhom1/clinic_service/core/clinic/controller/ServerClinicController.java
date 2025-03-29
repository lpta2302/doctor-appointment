package com.nhom1.clinic_service.core.clinic.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.clinic_service.core.clinic.entity.Clinic;
import com.nhom1.clinic_service.core.clinic.service.ClinicService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server/clinics")
@RequiredArgsConstructor
public class ServerClinicController {
    private final ClinicService clinicService;

    @GetMapping("/{clinicId}")
    public ResponseEntity<Clinic> findById(@PathVariable Long clinicId) {
        return ResponseEntity.ok(clinicService.findById(clinicId));
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Clinic>> findAllById(
        @RequestParam List<Long> clinicIds) {
        return ResponseEntity.ok(clinicService.findAllById(clinicIds));
    }
    
}
