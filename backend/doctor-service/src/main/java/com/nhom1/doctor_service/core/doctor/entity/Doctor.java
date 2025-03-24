package com.nhom1.doctor_service.core.doctor.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.doctor_service.core.doctor.enums.Gender;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue
    @Schema(accessMode=READ_ONLY)
    @Column(name="doctor_id")
    private Long id;

    @Version
    @JsonIgnore
    private Long version;

    @Enumerated(STRING)
    private Gender gender;

    @Column(length=200, unique = true)
    @Size(min=1, max=200, message="code has 1-200 characters")
    private String code;

    @Column(length=200)
    @Size(min=1, max=200, message="first name has 1-200 characters")
    private String firstName;
    
    @Column(length=200)
    @Size(min=1, max=200, message="last name has 1-200 characters")
    private String lastName;
    
    @Column(length=2000)
    @Size(min=1, max=2000, message="description has 1-2000 characters")
    private String description;

    @Column(length=10)
    @Size(min=1, max=10, message="last name has 10 characters")
    @Pattern(regexp = "^(0)[0-9]{9}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Column(length=200)
    @Size(min=1, max=200, message="workplace has 1-200 characters")
    private String workplace;

    @Column(length=200)
    @Size(min=1, max=200, message="qualification has 1-200 characters")
    private String qualification;
    
    private LocalDate dateOfBirth;

    private Integer yearsOfExperience;

    @Schema(accessMode=READ_ONLY)
    public String getFullName(){
        return firstName + " " + lastName;
    }

    @ManyToMany
    @JoinTable(
        name="doctors_specializations",
        joinColumns=@JoinColumn(name="doctor_id"),
        inverseJoinColumns=@JoinColumn(name="specialization_id")
    )
    private List<Specialization> specializations;

    public void setSpecializations(List<Specialization> specializations){
        if (this.specializations == null) {
            this.specializations = new ArrayList<>();
        } else {
            this.specializations.clear();
        }

        specializations.forEach(
            spec->this.specializations.add(spec)
        );
    }
}
