package com.nhom1.clinic_service.core.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom1.clinic_service.core.entity.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long>{
    Page<Clinic> findAll(Specification<Clinic> specification, Pageable pageable);

    Page<Clinic> findAllByIdIn(List<Long> clinicIds, Pageable pageable);
}
