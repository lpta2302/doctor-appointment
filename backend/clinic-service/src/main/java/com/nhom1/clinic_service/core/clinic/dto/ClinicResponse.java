package com.nhom1.clinic_service.core.clinic.dto;

import com.nhom1.clinic_service.core.specialization.dto.SpecializationResponse;

import lombok.Builder;

@Builder
public record ClinicResponse (
    Long id,
    String code,
    String name,
    SpecializationResponse specialization
){}
