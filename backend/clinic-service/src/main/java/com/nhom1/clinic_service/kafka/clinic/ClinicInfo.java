package com.nhom1.clinic_service.kafka.clinic;

import lombok.Builder;

@Builder
public record ClinicInfo(
    Long id,
    String code,
    String name,
    Long specializationId,
    String specializationName
) {}
