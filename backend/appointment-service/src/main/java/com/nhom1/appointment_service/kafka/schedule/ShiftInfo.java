package com.nhom1.appointment_service.kafka.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import lombok.Builder;

@Builder
public record ShiftInfo(
    Long id,
    Long clinicId,
    LocalDate appliedDate,
    Map<LocalTime, LocalTime> shiftTime
) {}
