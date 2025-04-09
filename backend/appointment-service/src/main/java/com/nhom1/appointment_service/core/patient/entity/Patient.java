package com.nhom1.appointment_service.core.patient.entity;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.appointment_service.core.appointment.entity.Appointment;
import com.nhom1.appointment_service.core.patient.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {
    @EmbeddedId
    private PatientId patientId;

    @Version
    @JsonIgnore
    private Long version;

    @Enumerated(STRING)
    private Gender gender;
    
    @Column(length=2000)
    @Size(min=1, max=2000, message="address has 1-2000 characters")
    private String address;
    
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "patient" ,cascade = ALL, orphanRemoval = false)
    @Schema(accessMode = READ_ONLY)
    private List<Appointment> appointments;

    public void addAppointment(Appointment appointment){
        if (appointments == null) {
            appointments = new ArrayList<>();
        }

        appointments.add(appointment);
        appointment.setPatient(this);
    }
}