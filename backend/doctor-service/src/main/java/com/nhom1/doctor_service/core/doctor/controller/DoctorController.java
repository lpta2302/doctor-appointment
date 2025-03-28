package com.nhom1.doctor_service.core.doctor.controller;

import java.util.HashMap;
import java.util.Map;
import org.springdoc.core.annotations.ParameterObject;
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
            @ParameterObject
            @PageableDefault(page=0, size=15) 
            Pageable pageable) {
        return ResponseEntity.ok(doctorService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<DoctorResponse>> search(
            @ParameterObject
            @PageableDefault(page=0, size=15) 
            Pageable pageable,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long specializationId
        ) {
        Map<String, String> params = new HashMap<>();
        if (id != null) params.put("id", id.toString());
        if (code != null) params.put("code", code);
        if (name != null) params.put("name", name);
        if (specializationId != null) params.put("specialization", specializationId.toString());

        
        return ResponseEntity.ok(doctorService.search(params, pageable));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<Doctor> findById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.findById(doctorId));
    }
}
