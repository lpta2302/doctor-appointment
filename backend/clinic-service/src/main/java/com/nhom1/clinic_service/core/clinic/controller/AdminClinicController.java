package com.nhom1.clinic_service.core.clinic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.clinic_service.common.PageResponse;
import com.nhom1.clinic_service.core.clinic.entity.Clinic;
import com.nhom1.clinic_service.core.clinic.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom1.clinic_service.core.clinic.dto.ClinicResponse;




@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/clinics")
public class AdminClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid Clinic clinic) {
        return ResponseEntity.ok(clinicService.create(clinic));
    }

    @PutMapping("/{clinicId}")
    public ResponseEntity<Long> update(@PathVariable Long clinicId, @RequestBody @Valid Clinic clinic) {
        return ResponseEntity.ok(clinicService.update(clinicId, clinic));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ClinicResponse>> findAll(
        @ParameterObject
        @PageableDefault(page = 0, size = 15) Pageable pageable) {
        return ResponseEntity.ok(clinicService.findAllWithSpecialization(pageable));
    }
    
    @GetMapping("/{clinicId}")
    public ResponseEntity<ClinicResponse> findById(@PathVariable Long clinicId) {
        return ResponseEntity.ok(clinicService.findWithSpecializationById(clinicId));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ClinicResponse>> search(
        @ParameterObject
        @PageableDefault(page = 0, size = 15) Pageable pageable,
        @RequestParam(required=false) String code, 
        @RequestParam(required=false) String name,
        @RequestParam(required=false) Long specializationId
        ) {
        return ResponseEntity.ok(clinicService.searchAllWithSpecialization(code, name,specializationId ,pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long clinicId) {
        clinicService.deleteById(clinicId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllById(@RequestBody List<Long> clinicIds) {
        clinicService.deleteAllById(clinicIds);
        return ResponseEntity.noContent().build();
    }
    
}
