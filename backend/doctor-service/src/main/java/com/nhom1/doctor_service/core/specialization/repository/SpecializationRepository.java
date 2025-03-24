package com.nhom1.doctor_service.core.specialization.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long>{
    Page<Specialization> findAll(Specification<Specialization> specification, Pageable pageable);
}
