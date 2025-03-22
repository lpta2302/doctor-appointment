package com.nhom1.doctor_service.core.doctor.dto;

import java.time.LocalDate;
import java.util.List;
import com.nhom1.doctor_service.core.doctor.enums.Gender;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DoctorRequest(
    @Size(min=1, max=200, message="code has 1-200 characters")
    String code,

    @Size(min=1, max=200, message="first name has 1-200 characters")
    String firstName,
    
    @Size(min=1, max=200, message="last name has 1-200 characters")
    String lastName,
    
    Gender gender,
    
    @Size(min=1, max=2000, message="description has 1-2000 characters")
    String description,
    
    @Size(min=1, max=10, message="last name has 10 characters")
    @Pattern(regexp = "^(0)[0-9]{9}$", message = "Invalid phone number format")
    String phoneNumber,
    
    @Size(min=1, max=200, message="workplace has 1-200 characters")
    String workplace,
    
    @Size(min=1, max=200, message="qualification has 1-200 characters")
    String qualification,
    
    LocalDate dateOfBirth,

    Integer yearsOfExperience,

    List<Long> specializationIds
) {}
