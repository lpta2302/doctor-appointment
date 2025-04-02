package com.nhom1.clinic_service.core.specialization.client;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.nhom1.clinic_service.core.specialization.dto.SpecializationResponse;

@FeignClient(
    name="doctor-service",
    url = "${application.config.specialization-url}"    
)
public interface SpecializationClient {
    @GetMapping("/{specializationId}/exists")
    ResponseEntity<Void> checkById(@PathVariable Long specializationId);
    @GetMapping("/{specializationId}")
    Optional<SpecializationResponse> findById(@PathVariable Long specializationId);
    @GetMapping("/ids")
    List<SpecializationResponse> findAllById(@RequestParam List<Long> specializationIds);
    
}

