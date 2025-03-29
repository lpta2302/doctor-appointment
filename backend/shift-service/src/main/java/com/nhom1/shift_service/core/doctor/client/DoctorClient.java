package com.nhom1.shift_service.core.doctor.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.nhom1.shift_service.core.doctor.dto.DoctorResponse;

@FeignClient(
    name = "doctor-service",
    url = "${application.config.doctor-url}"
)
public interface DoctorClient {
    @GetMapping("/{doctorId}")
    List<DoctorResponse> findById(Long doctorId);
    @GetMapping("/ids")
    List<DoctorResponse> findAllById(@RequestParam List<Long> doctorIds);
}
