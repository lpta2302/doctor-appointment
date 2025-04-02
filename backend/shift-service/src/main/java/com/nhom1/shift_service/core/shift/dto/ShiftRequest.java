package com.nhom1.shift_service.core.shift.dto;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ShiftRequest(
    @NotNull(message = "doctor id can't be null")
    @PositiveOrZero(message = "doctor id must be equal or greater than 0")
    Long doctorId,

    @NotNull
    @Schema(type = "string", pattern = "HH:mm", example = "12:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime startTime,
    
    @NotNull
    @Schema(type = "string", pattern = "HH:mm", example = "12:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime endTime
) {}
