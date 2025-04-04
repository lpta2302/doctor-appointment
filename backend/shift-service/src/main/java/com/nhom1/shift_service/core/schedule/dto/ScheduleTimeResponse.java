package com.nhom1.shift_service.core.schedule.dto;

import java.time.LocalTime;
import java.util.Map;
import lombok.Builder;

@Builder
public record ScheduleTimeResponse(
    String clinicName,
    String specializationName,
    Map<LocalTime, LocalTime> shiftsTime
) {}
