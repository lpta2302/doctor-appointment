package com.nhom1.clinic_service.core.clinic.mapper;

import org.springframework.stereotype.Component;

import com.nhom1.clinic_service.core.clinic.dto.ClinicResponse;
import com.nhom1.clinic_service.core.clinic.entity.Clinic;
import com.nhom1.clinic_service.core.specialization.dto.SpecializationResponse;

@Component
public class ClinicMapper {
    public ClinicResponse convertClinicResponseFrom(Clinic clinic, SpecializationResponse specializationResponse){
        return ClinicResponse.builder()
            .id(clinic.getId())
            .code(clinic.getCode())
            .name(clinic.getName())
            .specializationName(clinic.getSpecializationName())
            .specialization(specializationResponse)
            .build();
    }
}
