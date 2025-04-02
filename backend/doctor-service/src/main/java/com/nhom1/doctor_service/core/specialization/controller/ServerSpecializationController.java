package com.nhom1.doctor_service.core.specialization.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;
import com.nhom1.doctor_service.core.specialization.service.SpecializationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server/specializations")
@RequiredArgsConstructor
public class ServerSpecializationController {

    private final SpecializationService specializationService;

    @GetMapping("/{specializationId}/exists")
    public ResponseEntity<Void> checkById(@PathVariable Long specializationId) {
        if (specializationService.checkById(specializationId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{specializationId}")
    public ResponseEntity<Specialization> findById(@PathVariable Long specializationId) {
        return ResponseEntity.ok(specializationService.findById(specializationId));

    }
    
    @GetMapping("/ids")
    public ResponseEntity<List<Specialization>> findAllById(@RequestParam List<Long> specializationIds) {
        return ResponseEntity.ok(specializationService.findAllById(specializationIds));
    }
}
