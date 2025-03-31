package com.nhom1.clinic_service.core.clinic.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.clinic_service.common.PageResponse;
import com.nhom1.clinic_service.core.clinic.dto.ClinicResponse;
import com.nhom1.clinic_service.core.clinic.service.ClinicService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clinics")
public class ClinicController {
    private final ClinicService clinicService;

    @GetMapping
    public ResponseEntity<PageResponse<ClinicResponse>> findAll(
        @ParameterObject
        @PageableDefault(page = 0, size = 15) 
        Pageable pageable) {
        return ResponseEntity.ok(clinicService.findAllWithSpecialization(pageable));
    }
    
    @GetMapping("/{clinicId}")
    public ResponseEntity<ClinicResponse> findById(@PathVariable Long clinicId) {
        return ResponseEntity.ok(clinicService.findWithSpecializationById(clinicId));
    }
    @GetMapping("/search")
    public ResponseEntity<PageResponse<ClinicResponse>> search(
        @ParameterObject
        @PageableDefault(page = 0, size = 15) 
        Pageable pageable,
        @RequestParam(required=false) String code, 
        @RequestParam(required=false) String name,
        @RequestParam(required=false) Long specializationId
        ) {
        return ResponseEntity.ok(clinicService.searchAllWithSpecialization(code, name, specializationId, pageable));
    }
}
