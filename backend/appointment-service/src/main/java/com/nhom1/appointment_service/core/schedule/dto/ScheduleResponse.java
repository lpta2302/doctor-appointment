package com.nhom1.appointment_service.core.schedule.dto;

import java.time.LocalTime;
import java.util.Map;
import lombok.Builder;

@Builder
public record ScheduleResponse(
    String clinicName,
    String specializationName,
    Map<LocalTime, LocalTime> shiftsTime
) {}
