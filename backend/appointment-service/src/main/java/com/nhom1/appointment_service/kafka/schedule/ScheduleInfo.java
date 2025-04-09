package com.nhom1.appointment_service.kafka.schedule;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ScheduleInfo(
    Long clinicId,
    LocalDate appliedDate
) {
}
