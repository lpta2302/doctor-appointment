package com.nhom1.appointment_service.core.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AppointmentRequest(
    @NotNull
    LocalDate appointmentDate,

    @NotNull
    @Schema(type = "string", pattern = "HH:mm", example = "12:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime appointmentTime,

    @PositiveOrZero(message = "clinic id must be equal or greater than 0")
    Long clinicId,

    boolean isOldPatient,

    @Valid
    Patient patient
) {}
