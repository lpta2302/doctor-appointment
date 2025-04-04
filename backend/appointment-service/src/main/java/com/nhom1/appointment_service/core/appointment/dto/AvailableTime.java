package com.nhom1.appointment_service.core.appointment.dto;

import java.time.LocalTime;
import lombok.Builder;

@Builder
public record AvailableTime(
    LocalTime time,
    boolean isAvailable
) {}
