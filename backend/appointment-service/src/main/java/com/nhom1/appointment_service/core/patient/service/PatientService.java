package com.nhom1.appointment_service.core.patient.service;

import static com.nhom1.appointment_service.common.StringCustomizer.APPROXIMATE_COMPARATOR.BOTH;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nhom1.appointment_service.common.PageResponse;
import com.nhom1.appointment_service.common.StringCustomizer;
import com.nhom1.appointment_service.core.patient.dto.PatientRequest;
import com.nhom1.appointment_service.core.patient.dto.PatientResponse;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import com.nhom1.appointment_service.core.patient.mapper.PatientMapper;
import com.nhom1.appointment_service.core.patient.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientMapper patientMapper;

    private final PatientRepository patientRepository;

    public Patient create(PatientRequest request){
        return patientRepository.save(patientMapper.convertToPatientFrom(request));
    }

    public Patient save(Patient patient){
        return patientRepository.save(patient);
    }

    public void deleteById(String phoneNumber, String fullname){
        patientRepository.deleteByPhoneNumberAndFullname(phoneNumber, fullname);
    }

    public PatientResponse findPatientDetailByPhoneNumberAndFullname(String phoneNumber, String fullname) {
        return patientMapper.convertToPatientResponseFrom(
            patientRepository.findByPhoneNumberAndFullname(phoneNumber, StringCustomizer.convertApproximateCompare(fullname, BOTH))
            .orElseThrow(()->new EntityNotFoundException("not found patient"))
        );
    }

    public Patient findByPhoneNumberAndFullname(String phoneNumber, String fullname) {
        return patientRepository.findByPhoneNumberAndFullname(phoneNumber, StringCustomizer.convertApproximateCompare(fullname, BOTH))
        .orElseThrow(()->new EntityNotFoundException("not found patient"));
    }

    public PageResponse<PatientResponse> search(String phoneNumber, String fullname, Pageable pageable) {
        var pageResult = 
            patientRepository.search(phoneNumber, StringCustomizer.convertApproximateCompare(fullname, BOTH), pageable);
        return PageResponse.fromPage(
            pageResult, 
            pageResult.map(patientMapper::convertToPatientResponseFrom).toList()
        );
    }

}
