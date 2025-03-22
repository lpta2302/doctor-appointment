package com.nhom1.doctor_service.core.doctor.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.doctor_service.common.PageResponse;
import com.nhom1.doctor_service.core.doctor.dto.DoctorResponse;
import com.nhom1.doctor_service.core.doctor.entity.Doctor;
import com.nhom1.doctor_service.core.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<PageResponse<DoctorResponse>> findAll(
            @PageableDefault(page=0, size=15) 
            Pageable pageable) {
        return ResponseEntity.ok(doctorService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<DoctorResponse>> findAll(
            @PageableDefault(page=0, size=15) 
            Pageable pageable,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(doctorService.findByNameOrCode(code, name, pageable));
    }

    @GetMapping("/{DoctorId}")
    public ResponseEntity<Doctor> findById(@PathVariable Long DoctorId) {
        return ResponseEntity.ok(doctorService.findById(DoctorId));
    }
}
