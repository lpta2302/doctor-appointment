package com.nhom1.doctor_service.core.doctor.dto;

import java.time.LocalDate;
import java.util.List;
import com.nhom1.doctor_service.core.doctor.enums.Gender;
import lombok.Builder;

@Builder
public record DoctorResponse(
    String code,
    String firstName,    
    String lastName,
    String fullName,
    Gender gender,
    String description,
    String phoneNumber,
    String workplace,
    String qualification,
    LocalDate dateOfBirth,
    Integer yearsOfExperience,
    List<String> specializations
) {}
