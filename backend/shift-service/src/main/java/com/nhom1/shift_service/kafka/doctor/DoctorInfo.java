package com.nhom1.shift_service.kafka.doctor;

import lombok.Builder;

@Builder
public record DoctorInfo(
    Long id,
    String code,
    String fullname,
    String phoneNumber
) 
{}
