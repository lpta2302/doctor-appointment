package com.nhom1.shift_service.kafka.shift;

import java.time.LocalTime;
import lombok.Builder;

@Builder
public record ShiftInfo(
    Long id,
    LocalTime startTime,
    LocalTime endTime
) {
}
