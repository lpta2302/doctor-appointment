package com.nhom1.shift_service.core.doctor.client;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom1.shift_service.core.doctor.dto.DoctorResponse;

@FeignClient(
    name = "doctor-service",
    url = "${application.config.doctor-url}"
)
public interface DoctorClient {
    @GetMapping("/{doctorId}/exists")
    List<DoctorResponse> checkById(Long doctorId);
    @GetMapping("/{doctorId}")
    Optional<DoctorResponse> findById(@PathVariable Long doctorId);
    @GetMapping("/ids/exist")
    List<DoctorResponse> checkAllById(@RequestParam List<Long> doctorIds);
    @GetMapping("/ids")
    List<DoctorResponse> findAllById(@RequestParam Set<Long> doctorIds);
}
