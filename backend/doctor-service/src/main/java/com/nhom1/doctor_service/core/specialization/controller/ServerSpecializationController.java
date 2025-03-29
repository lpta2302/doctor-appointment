package com.nhom1.doctor_service.core.specialization.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;
import com.nhom1.doctor_service.core.specialization.service.SpecializationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server/specializations")
@RequiredArgsConstructor
public class ServerSpecializationController {

    private final SpecializationService specializationService;

    @GetMapping("/{specializationId}")
    public ResponseEntity<Specialization> findById(@PathVariable Long specializationId) {
        return ResponseEntity.ok(specializationService.findById(specializationId));

    }
    
    @GetMapping("/ids")
    public ResponseEntity<List<Specialization>> findAllById(@RequestBody List<Long> specializationIds) {
        return ResponseEntity.ok(specializationService.findAllById(specializationIds));
    }
}
