package com.nhom1.doctor_service.core.doctor.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhom1.doctor_service.common.PageResponse;
import com.nhom1.doctor_service.core.doctor.dto.DoctorRequest;
import com.nhom1.doctor_service.core.doctor.dto.DoctorResponse;
import com.nhom1.doctor_service.core.doctor.entity.Doctor;
import com.nhom1.doctor_service.core.doctor.mapper.DoctorMapper;
import com.nhom1.doctor_service.core.doctor.repository.DoctorRepository;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;
import com.nhom1.doctor_service.core.specialization.service.SpecializationService;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    private final SpecializationService specializationService;

    public Long create(DoctorRequest doctorRequest){
        Doctor doctor = doctorMapper.convertDoctorFrom(doctorRequest);
        
        List<Specialization> specializations = 
            specializationService.findAllByIds(doctorRequest.specializationIds());
        doctor.setSpecializations(specializations);

        return doctorRepository.save(doctor).getId();
    }

    public Long update(Long doctorId ,DoctorRequest doctorRequest){
        Doctor doctor = findById(doctorId);
        
        List<Specialization> specializations = 
            specializationService.findAllByIds(doctorRequest.specializationIds());
        doctor.setSpecializations(specializations);

        return doctorRepository.save(doctor).getId();
    }

    public Doctor findById(Long doctorId){
        return doctorRepository.findById(doctorId)
            .orElseThrow(()->new NotFoundException("Not found doctor with id: "+doctorId));
    }

    public List<Doctor> findAllById(List<Long> doctorIds){
        return doctorRepository.findAllById(doctorIds);
    }

    public PageResponse<DoctorResponse> findAll(Pageable pageable){
        Page<Doctor> pageResult = doctorRepository.findAll(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult
                .getContent()
                .stream()
                .map(doctorMapper::convertDoctorResponseFrom)
                .toList());
    }

    public PageResponse<DoctorResponse> findByNameOrCode(String code, String name, Pageable pageable) {
        Page<Doctor> pageResult = doctorRepository.findByCodeOrName(code,"%" + name.toLowerCase() + "%", pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult
                .getContent()
                .stream()
                .map(doctorMapper::convertDoctorResponseFrom)
                .toList());
    }
    
    public void deleteById(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    public void deleteAllById(List<Long> doctorIds) {
        doctorRepository.deleteAllById(doctorIds);
    }

}
