package com.nhom1.appointment_service.core.patient.mapper;

import org.springframework.stereotype.Component;
import com.nhom1.appointment_service.core.patient.dto.PatientRequest;
import com.nhom1.appointment_service.core.patient.dto.PatientResponse;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import com.nhom1.appointment_service.core.patient.entity.PatientId;

@Component
public class PatientMapper {

    public Patient convertToPatientFrom(PatientRequest request) {
        PatientId id = PatientId.builder()
            .phoneNumber(request.phoneNumber())
            .fullname(request.fullname())
            .build();

        return Patient.builder()
            .patientId(id)
            .address(request.address())
            .dateOfBirth(request.dateOfBirth())
            .gender(request.gender())
            .build();
    }

    public PatientResponse convertToPatientResponseFrom(Patient patient) {
        return PatientResponse.builder()
            .fullname(patient.getPatientId().getFullname())
            .phoneNumber(patient.getPatientId().getPhoneNumber())
            .address(patient.getAddress())
            .dateOfBirth(patient.getDateOfBirth())
            .gender(patient.getGender())
            .appointments(patient.getAppointments())
            .build();
    }
    
}
