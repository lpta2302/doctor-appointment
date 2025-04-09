package com.nhom1.appointment_service.kafka.clinic;

import lombok.Builder;

@Builder
public record ClinicInfo(
    Long id,
    String code,
    String name,
    Long specializationId,
    String specializationName
) {}
