package com.nhom1.shift_service.core.clinic.dto;

public record ClinicResponse(
    Long id,
    Long specializationId,
    String name, 
    String specializationName, 
    String code
) {
    
}
