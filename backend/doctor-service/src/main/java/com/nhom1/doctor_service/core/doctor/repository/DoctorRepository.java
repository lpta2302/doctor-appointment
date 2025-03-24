package com.nhom1.doctor_service.core.doctor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nhom1.doctor_service.core.doctor.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    @Query("""
        select d
        from Doctor d
        where d.code = :code or
        lower(concat(d.firstName, ' ', d.lastName)) like :name        
    """)
    Page<Doctor> findByCodeOrName(String code, String name, Pageable pageable);

    Page<Doctor> findAll(Specification<Doctor> spec, Pageable pageable);
}
