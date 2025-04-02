package com.nhom1.doctor_service.core.doctor.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nhom1.doctor_service.core.doctor.entity.Doctor;
import com.nhom1.doctor_service.core.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server/doctors")
@RequiredArgsConstructor
public class ServerDoctorController {
    private final DoctorService doctorService;
    
    @GetMapping("/{doctorId}/exists")
    public ResponseEntity<Void> checkById(@PathVariable Long doctorId) {
        if (doctorService.checkById(doctorId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{doctorId}")
    public ResponseEntity<Doctor> findById(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.findById(doctorId));
    }

    @GetMapping("/ids/exist")
    public ResponseEntity<Void> checkAllById(@RequestParam List<Long> doctorIds) {
        if (doctorService.checkAllExistById(doctorIds)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Doctor>> findAllById(@RequestParam List<Long> doctorIds) {
        return ResponseEntity.ok(doctorService.findAllById(doctorIds));
    }
}
