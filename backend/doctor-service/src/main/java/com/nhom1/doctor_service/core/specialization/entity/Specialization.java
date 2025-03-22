package com.nhom1.doctor_service.core.specialization.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.doctor_service.core.doctor.entity.Doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.DecimalMin;
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
@Table(name = "specializations")
public class Specialization {
    @Id
    @GeneratedValue
    @Schema(accessMode=READ_ONLY)
    @Column(name="specialization_id")
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

    @Column(columnDefinition = "NUMERIC(10,2)")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @ManyToMany(mappedBy="specializations")
    private List<Doctor> doctors;
}
