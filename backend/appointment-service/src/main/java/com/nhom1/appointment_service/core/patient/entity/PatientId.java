package com.nhom1.appointment_service.core.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
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
@Embeddable
public class PatientId {
    @Column(length=200)
    @Size(min=1, max=200, message="fullname has 1-400 characters")
    private String fullname;

    @Column(length=10)
    @Size(min=1, max=10, message="last name has 10 characters")
    @Pattern(regexp = "^(0)[0-9]{9}$", message = "Invalid phone number format")
    private String phoneNumber;
}
