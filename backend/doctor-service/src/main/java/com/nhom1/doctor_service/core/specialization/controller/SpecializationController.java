package com.nhom1.doctor_service.core.specialization.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.doctor_service.common.PageResponse;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;
import com.nhom1.doctor_service.core.specialization.service.SpecializationService;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<PageResponse<Specialization>> findAll(
        @PageableDefault(page=0, size=15)
        Pageable pageable) {
        return ResponseEntity.ok(specializationService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<Specialization>> findAll(
        @PageableDefault(page=0, size=15)
        Pageable pageable,
        @RequestParam(required = false) String code,
        @RequestParam(required = false) String name) {
        return ResponseEntity.ok(specializationService.findByNameOrCode(code, name, pageable));
    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<Specialization> findById(@PathVariable Long specializationId) {
        return ResponseEntity.ok(specializationService.findById(specializationId));
    }
    
}
