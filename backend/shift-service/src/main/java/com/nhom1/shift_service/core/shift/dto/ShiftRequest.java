package com.nhom1.shift_service.core.shift.dto;

import java.time.LocalTime;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ShiftRequest(
    @NotNull(message = "doctor id can't be null")
    @PositiveOrZero(message = "doctor id must be equal or greater than 0")
    Long doctorId,
    
    @NotNull(message = "specializationId id can't be null")
    @PositiveOrZero(message = "specializationId id must be equal or greater than 0")
    Long specializationId,

    @NotNull
    LocalTime startTime,

    @NotNull
    LocalTime endTime
) {}
