package com.nhom1.appointment_service.core.appointment.entity;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.appointment_service.core.appointment.enums.AppointmentStatus;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue
    @Schema(accessMode=READ_ONLY)
    @Column(name="appointment_id")
    private Long id;

    @Column(length=20, unique = true)
    @Size(min=1, max=20, message="code has 1-20 characters")
    private String code;

    @Version
    @JsonIgnore
    private Long version;

    @Enumerated(STRING)
    private AppointmentStatus status;

    @NotNull
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    @NotNull
    @PositiveOrZero()
    private Long specializationId;

    private String specializationName;
    
    @NotNull
    @PositiveOrZero
    private Long clinicId;

    private String clinicName;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    private Patient patient;
}