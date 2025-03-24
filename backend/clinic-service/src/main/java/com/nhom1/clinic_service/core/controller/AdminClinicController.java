package com.nhom1.clinic_service.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.clinic_service.common.PageResponse;
import com.nhom1.clinic_service.core.entity.Clinic;
import com.nhom1.clinic_service.core.service.ClinicService;
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




@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/clinics")
public class AdminClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody Clinic clinic) {
        return ResponseEntity.ok(clinicService.create(clinic));
    }

    @PutMapping("/{clinicId}")
    public ResponseEntity<Long> update(@PathVariable Long clinicId, @RequestBody Clinic clinic) {
        return ResponseEntity.ok(clinicService.update(clinicId, clinic));
    }

    @GetMapping
    public ResponseEntity<PageResponse<Clinic>> findAll(
        @ParameterObject
        @PageableDefault(page = 0, size = 15) Pageable pageable) {
        return ResponseEntity.ok(clinicService.findAll(pageable));
    }
    
    @GetMapping("/{clinicId}")
    public ResponseEntity<Clinic> findById(@PathVariable Long clinicId) {
        return ResponseEntity.ok(clinicService.findById(clinicId));
    }

    @GetMapping("/ids")
    public ResponseEntity<PageResponse<Clinic>> findAllById(
        @ParameterObject
        @PageableDefault(page = 0, size = 15) Pageable pageable,
        @RequestParam List<Long> clinicIds) {
        return ResponseEntity.ok(clinicService.findAllById(clinicIds, pageable));
    }
    
    @GetMapping("/search")
    public ResponseEntity<PageResponse<Clinic>> findByCodeOrName(
        @ParameterObject
        @PageableDefault(page = 0, size = 15) Pageable pageable,
        @RequestParam(required=false) String code, 
        @RequestParam(required=false) String name
        ) {
        return ResponseEntity.ok(clinicService.findAllByCodeOrName(code, name, pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long clinicId) {
        clinicService.deleteById(clinicId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllById(@RequestParam List<Long> clinicIds) {
        clinicService.deleteAllById(clinicIds);
        return ResponseEntity.noContent().build();
    }
    
    
}
