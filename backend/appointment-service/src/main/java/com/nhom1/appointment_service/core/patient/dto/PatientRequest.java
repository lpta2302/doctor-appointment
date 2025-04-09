package com.nhom1.appointment_service.core.patient.dto;

import static jakarta.persistence.EnumType.STRING;
import java.time.LocalDate;
import com.nhom1.appointment_service.core.patient.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PatientRequest(
    @Column(length=200)
    @Size(min=1, max=200, message="fullname has 1-400 characters")
    String fullname,

    @Column(length=10)
    @Size(min=1, max=10, message="phone number has 10 characters")
    @Pattern(regexp = "^(0)[0-9]{9}$", message = "Invalid phone number format")
    String phoneNumber,

    @Enumerated(STRING)
    Gender gender,
    
    @Column(length=2000)
    @Size(min=1, max=2000, message="address has 1-2000 characters")
    String address,
    
    LocalDate dateOfBirth
) {}
