package com.nhom1.shift_service.core.clinic.client;

import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.nhom1.shift_service.core.clinic.dto.ClinicResponse;

@FeignClient(
    name = "clinic-service",
    url = "${application.config.clinic-url}"
)
public interface ClinicClient {
    @GetMapping("/{clinicId}/exists")
    ResponseEntity<Void> checkExistById(@PathVariable Long clinicId);
    @GetMapping("/{clinicId}")
    Optional<ClinicResponse> findById(@PathVariable Long clinicId);
}
