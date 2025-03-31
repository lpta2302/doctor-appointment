package com.nhom1.shift_service.core.doctor.dto;

import lombok.Builder;

@Builder
public record DoctorResponse(
    Long id,
    String code,
    String fullname,
    String phoneNumber
) {}
