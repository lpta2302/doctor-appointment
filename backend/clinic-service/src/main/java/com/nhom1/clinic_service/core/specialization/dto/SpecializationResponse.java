package com.nhom1.clinic_service.core.specialization.dto;

import lombok.Builder;

@Builder
public record SpecializationResponse(
    Long id,
    String name
) {}
