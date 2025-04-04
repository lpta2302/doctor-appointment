package com.nhom1.appointment_service.core.schedule.client;

import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.nhom1.appointment_service.core.schedule.dto.ScheduleResponse;

@FeignClient(
    name = "shift-service",
    url = "${application.config.schedule-url}"
)
public interface ScheduleClient {
    @GetMapping("/{clinicId}/{appliedDate}")
    Optional<ScheduleResponse> findById(
        @PathVariable Long clinicId,
        @PathVariable String appliedDate
    );
    @GetMapping("/{clinicId}/{appliedDate}/time")
    Optional<ScheduleResponse> findScheduleTimeById(
        @PathVariable Long clinicId,
        @PathVariable String appliedDate
    );
}
