package com.nhom1.appointment_service.core.patient.service;

import org.springframework.stereotype.Service;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import com.nhom1.appointment_service.core.patient.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient create(Patient patient){
        return patientRepository.save(patient);
    }

    public Long updatePatient(Long patientId ,Patient patient){
        Patient updatingPatient = findById(patientId);

        updatingPatient.setFullname(patient.getFullname());
        updatingPatient.setDateOfBirth(patient.getDateOfBirth());
        updatingPatient.setGender(patient.getGender());
        updatingPatient.setPhoneNumber(patient.getPhoneNumber());
        updatingPatient.setAddress(patient.getAddress());

        return patientRepository.save(patient).getId();
    }

    public Patient save(Patient patient){
        return patientRepository.save(patient);
    }

    public Patient findById(Long id){
        return patientRepository.findById(id)
            .orElseThrow(()->new EntityNotFoundException(
                "not found patient with id: "+id));
    }

    public Patient findByPhoneNumber(String phoneNumber){
        return patientRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(()->new EntityNotFoundException(
                "not found patient with phone number: "+phoneNumber));
    }

    public void deleteById(Long id){
        patientRepository.deleteById(id);
    }
}
