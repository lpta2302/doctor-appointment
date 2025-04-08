package com.nhom1.appointment_service.core.patient.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import com.nhom1.appointment_service.core.patient.entity.PatientId;

public interface PatientRepository extends JpaRepository<Patient, PatientId> {
    @Query("""
            select p
            from Patient p
            where LOWER(p.patientId.fullname) like :fullname
            and p.patientId.phoneNumber = :phoneNumber
            """)
    @EntityGraph(attributePaths = "appointments")
    Optional<Patient> findByPhoneNumberAndFullname(String phoneNumber, String fullname);

    @Modifying
    @Query("""
            delete from Patient p
            where p.patientId.phoneNumber = :phoneNumber
            and LOWER(p.patientId.fullname) = :fullname
            """)
    void deleteByPhoneNumberAndFullname(String phoneNumber, String fullname);

    @Query("""
            select p
            from Patient p
            where (:phoneNumber is null or p.patientId.phoneNumber = :phoneNumber)
            and (:fullname is null or (LOWER(p.patientId.fullname) like :fullname))
            """)
    @EntityGraph(attributePaths = "appointments")
    Page<Patient> search(String phoneNumber,
            String fullname, Pageable pageable);
    
}
