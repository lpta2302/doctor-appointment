package com.nhom1.shift_service.core.doctor.client;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.nhom1.shift_service.core.doctor.dto.DoctorResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(
    name = "doctor-service",
    url = "${application.config.doctor-url}"
)
public interface DoctorClient {
    @GetMapping("/{doctorId}/exists")
    List<DoctorResponse> checkById(Long doctorId);
    @GetMapping("/{doctorId}")
    Optional<DoctorResponse> findById(Long doctorId);
    @GetMapping("/ids/exist")
    List<DoctorResponse> checkAllById(@RequestBody List<Long> doctorIds);
    @GetMapping("/ids")
    List<DoctorResponse> findAllById(@RequestBody Set<Long> doctorIds);
}
