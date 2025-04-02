package com.nhom1.shift_service.core.shift.dto;

import java.time.LocalTime;

import com.nhom1.shift_service.core.doctor.dto.DoctorResponse;

import lombok.Builder;

@Builder
public record ShiftResponse(
    Long id,
    DoctorResponse doctor,
    LocalTime startTime,
    LocalTime endTime
) {}
