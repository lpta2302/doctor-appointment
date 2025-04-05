package com.nhom1.clinic_service.core.clinic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nhom1.clinic_service.core.clinic.entity.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long>{
    Page<Clinic> findAll(Specification<Clinic> specification, Pageable pageable);

    Page<Clinic> findAllByIdIn(List<Long> clinicIds, Pageable pageable);

    @Modifying
    @Query("""     
        update Clinic c
        set c.specializationId = null, c.specializationName = null
        where c.specializationId = :specializationId
    """)
    void deleteSpecializationDetailOfClinics(Long specializationId);

    @Modifying
    @Query("""     
        update Clinic c
        set c.specializationName = :specializationName
        where c.specializationId = :specializationId
    """)
    void updateSpecializationDetailOfClinics(Long specializationId, String specializationName);
}
