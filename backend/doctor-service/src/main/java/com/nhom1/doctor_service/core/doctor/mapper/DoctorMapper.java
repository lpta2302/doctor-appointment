package com.nhom1.doctor_service.core.doctor.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nhom1.doctor_service.core.doctor.dto.DoctorRequest;
import com.nhom1.doctor_service.core.doctor.dto.DoctorResponse;
import com.nhom1.doctor_service.core.doctor.entity.Doctor;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;

@Component
public class DoctorMapper {

    public Doctor convertDoctorFrom(DoctorRequest doctorRequest) {
        return Doctor.builder()
            .code(doctorRequest.code())
            .firstName(doctorRequest.firstName())
            .lastName(doctorRequest.lastName())
            .gender(doctorRequest.gender())
            .description(doctorRequest.description())
            .phoneNumber(doctorRequest.phoneNumber())
            .dateOfBirth(doctorRequest.dateOfBirth())
            .qualification(doctorRequest.qualification())
            .build();
    }

    public DoctorResponse convertDoctorResponseFrom(Doctor doctor){
        List<String> specializations = 
            doctor.getSpecializations().stream().map(Specialization::getName).toList();

        return DoctorResponse.builder()
            .code(doctor.getCode())
            .firstName(doctor.getFirstName())
            .lastName(doctor.getLastName())
            .fullName(doctor.getFullName())
            .gender(doctor.getGender())
            .description(doctor.getDescription())
            .phoneNumber(doctor.getPhoneNumber())
            .workplace(doctor.getWorkplace())
            .qualification(doctor.getQualification())
            .dateOfBirth(doctor.getDateOfBirth())
            .yearsOfExperience(doctor.getYearsOfExperience())
            .specializations(
                specializations
            )
            .build();
    }
}
