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
            .workplace(doctorRequest.workplace())
            .yearsOfExperience(doctorRequest.yearsOfExperience())
            .build();
    }

    public Doctor copyDoctorFrom(Doctor doctor, DoctorRequest doctorRequest) {
        if (!doctor.getCode().equals(doctorRequest.code())) {
            doctor.setCode(doctorRequest.code());
        }
        doctor.setFirstName(doctorRequest.firstName());
        doctor.setLastName(doctorRequest.lastName());
        doctor.setGender(doctorRequest.gender());
        doctor.setDescription(doctorRequest.description());
        doctor.setPhoneNumber(doctorRequest.phoneNumber());
        doctor.setDateOfBirth(doctorRequest.dateOfBirth());
        doctor.setQualification(doctorRequest.qualification());
        doctor.setWorkplace(doctorRequest.workplace());
        doctor.setYearsOfExperience(doctorRequest.yearsOfExperience());
        return doctor;
    }

    public DoctorResponse convertDoctorResponseFrom(Doctor doctor){
        List<String> specializations = 
            doctor.getSpecializations().stream().map(Specialization::getName).toList();

        return DoctorResponse.builder()
            .code(doctor.getCode())
            .firstName(doctor.getFirstName())
            .lastName(doctor.getLastName())
            .fullname(doctor.getFullname())
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
