package com.nhom1.shift_service.kafka.shift;

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
) {
}
