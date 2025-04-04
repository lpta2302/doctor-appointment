package com.nhom1.appointment_service.core.patient.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nhom1.appointment_service.core.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPhoneNumber(String phoneNumber);
    
}
