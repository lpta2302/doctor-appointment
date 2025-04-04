package com.nhom1.doctor_service.kafka;

import lombok.Builder;

@Builder
public record DoctorInfo(
    Long id,
    String code,
    String fullname,
    String phoneNumber
) 
{}
