package com.nhom1.appointment_service.core.patient.dto;

import java.time.LocalDate;
import java.util.List;
import com.nhom1.appointment_service.core.appointment.entity.Appointment;
import com.nhom1.appointment_service.core.patient.enums.Gender;
import lombok.Builder;

@Builder
public record PatientResponse(
    String fullname,
    String phoneNumber,
    Gender gender,
    String address,
    LocalDate dateOfBirth,
    List<Appointment> appointments
) {}
