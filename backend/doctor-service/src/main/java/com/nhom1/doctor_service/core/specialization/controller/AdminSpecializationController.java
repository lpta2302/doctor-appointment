package com.nhom1.doctor_service.core.specialization.controller;

import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.doctor_service.common.PageResponse;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;
import com.nhom1.doctor_service.core.specialization.service.SpecializationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/admin/specializations")
@RequiredArgsConstructor
public class AdminSpecializationController {

    private final SpecializationService specializationService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid Specialization specialization) {
        return ResponseEntity.ok(specializationService.create(specialization));
    }

    @PutMapping("/{specializationId}")
    public ResponseEntity<Long> update(
        @PathVariable Long specializationId, 
        @RequestBody Specialization specialization) {
        return ResponseEntity.ok(
            specializationService.update(specializationId, specialization)
        );
    }
    
    @GetMapping
    public ResponseEntity<PageResponse<Specialization>> findAll(
        @ParameterObject
        @PageableDefault(page=0, size=15)
        Pageable pageable) {
        return ResponseEntity.ok(specializationService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<Specialization>> search(
        @ParameterObject
        @PageableDefault(page=0, size=15)
        Pageable pageable,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String code
        ) {
        return ResponseEntity.ok(specializationService.findAllByCodeOrNameLike(code, name, pageable));
    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<Specialization> findById(@PathVariable Long specializationId) {
        return ResponseEntity.ok(specializationService.findById(specializationId));
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Specialization>> findAllById(@PathVariable List<Long> specializationIds) {
        return ResponseEntity.ok(specializationService.findAllById(specializationIds));
    }

    @DeleteMapping("/{specializationId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long specializationId) {
        specializationService.deleteById(specializationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllById(@RequestBody List<Long> specializationIds) {
        specializationService.deleteAllById(specializationIds);
        return ResponseEntity.noContent().build();
    }
    
}
