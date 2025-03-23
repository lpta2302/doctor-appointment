package com.nhom1.clinic_service.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.clinic_service.common.PageResponse;
import com.nhom1.clinic_service.core.entity.Clinic;
import com.nhom1.clinic_service.core.service.ClinicService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        @PageableDefault(page = 0, size = 15) Pageable pageable) {
        return ResponseEntity.ok(clinicService.findAll(pageable));
    }
    
    @GetMapping("/{clinicId}")
    public ResponseEntity<Clinic> findById(@PathVariable Long clinicId) {
        return ResponseEntity.ok(clinicService.findById(clinicId));
    }

    @GetMapping("/ids-in")
    public ResponseEntity<PageResponse<Clinic>> findAllById(
        @PageableDefault(page = 0, size = 15) Pageable pageable,
        @RequestParam List<Long> clinicIds) {
        return ResponseEntity.ok(clinicService.findAllById(clinicIds, pageable));
    }
    
    @GetMapping("/search")
    public ResponseEntity<PageResponse<Clinic>> search(
        @PageableDefault(page = 0, size = 15) Pageable pageable,
        @RequestParam Map<String, String> params) {
        return null;
    }
    
}
