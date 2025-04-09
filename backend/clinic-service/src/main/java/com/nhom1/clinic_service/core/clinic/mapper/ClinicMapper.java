package com.nhom1.clinic_service.core.clinic.mapper;

import org.springframework.stereotype.Component;

import com.nhom1.clinic_service.core.clinic.dto.ClinicResponse;
import com.nhom1.clinic_service.core.clinic.entity.Clinic;
import com.nhom1.clinic_service.core.specialization.dto.SpecializationResponse;
import com.nhom1.clinic_service.kafka.clinic.ClinicInfo;

@Component
public class ClinicMapper {
    public ClinicResponse convertClinicResponseFrom(Clinic clinic, SpecializationResponse specializationResponse){
        return ClinicResponse.builder()
            .id(clinic.getId())
            .code(clinic.getCode())
            .name(clinic.getName())
            .specializationId(clinic.getSpecializationId())
            .specializationName(clinic.getSpecializationName())
            .specialization(specializationResponse)
            .build();
    }

    public ClinicInfo convertClinicInfoFrom(Clinic clinic) {
        return ClinicInfo.builder()
            .id(clinic.getId())
            .code(clinic.getCode())
            .name(clinic.getName())
            .specializationId(clinic.getSpecializationId())
            .specializationName(clinic.getSpecializationName())
            .build();
    }
}
