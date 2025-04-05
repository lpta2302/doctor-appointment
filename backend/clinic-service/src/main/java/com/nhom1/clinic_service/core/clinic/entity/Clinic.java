package com.nhom1.clinic_service.core.clinic.entity;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.PositiveOrZero;
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
@Table(name = "clinics")
public class Clinic {
    @Id
    @GeneratedValue
    @Schema(accessMode=READ_ONLY)
    @Column(name="clinic_id")
    private Long id;

    @Version
    @JsonIgnore
    private Long version;

    @Column(length=200, unique = true)
    @Size(min=1, max=200, message="code has 1-200 characters")
    private String code;

    @Column(length=200)
    @Size(min=1, max=200, message="name has 1-200 characters")
    private String name;
    
    @PositiveOrZero(message = "specializationId must not be negative")
    private Long specializationId;
    
    @Column(length=200)
    @Size(min=1, max=200, message="specialization name has 1-200 characters")
    @Schema(accessMode=READ_ONLY)
    private String specializationName;
}
