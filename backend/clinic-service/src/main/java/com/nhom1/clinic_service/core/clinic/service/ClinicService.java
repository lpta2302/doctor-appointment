package com.nhom1.clinic_service.core.clinic.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nhom1.clinic_service.common.PageResponse;
import com.nhom1.clinic_service.core.clinic.dto.ClinicResponse;
import com.nhom1.clinic_service.core.clinic.entity.Clinic;
import com.nhom1.clinic_service.core.clinic.mapper.ClinicMapper;
import com.nhom1.clinic_service.core.clinic.repository.ClinicRepository;
import com.nhom1.clinic_service.core.clinic.specification.ClinicSpecifications;
import com.nhom1.clinic_service.core.specialization.client.SpecializationClient;
import com.nhom1.clinic_service.core.specialization.dto.SpecializationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final SpecializationClient specializationClient;
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public Long create(Clinic clinic) {
        SpecializationResponse specialization = 
                specializationClient.findById(clinic.getSpecializationId())
                .orElseThrow(()->new EntityNotFoundException(
                        "not found specialization with id: "+
                        clinic.getSpecializationId()));

        clinic.setSpecializationName(specialization.name());
        
        return clinicRepository.save(clinic).getId();
    }

    public Long update(Long clinicId, Clinic clinic) {
        Clinic foundClinic = findById(clinicId);
            
        if (!foundClinic.getCode().equals(clinic.getCode())) {
                foundClinic.setCode(clinic.getCode());
        }

        if (!foundClinic.getSpecializationId().equals(clinic.getSpecializationId())) {
                SpecializationResponse specialization = 
                specializationClient.findById(clinic.getSpecializationId())
                .orElseThrow(()->new EntityNotFoundException(
                        "not found specialization with id: "+
                        clinic.getSpecializationId()));

                foundClinic.setSpecializationId(clinic.getSpecializationId());
                foundClinic.setSpecializationName(specialization.name());
        } 
        foundClinic.setName(clinic.getName());

        return clinicRepository.save(foundClinic).getId();
    }

    public PageResponse<ClinicResponse> findAllWithSpecialization(Pageable pageable) {
        var pageResult = clinicRepository.findAll(pageable);

        List<SpecializationResponse> specializationResponses = specializationClient
                .findAllById(pageResult.stream().map(Clinic::getSpecializationId).toList());

        return PageResponse.fromPage(pageResult,
                pageResult.getContent().stream()
                        .map(clinic -> clinicMapper.convertClinicResponseFrom(clinic,
                                clinic.getSpecializationId() != null
                                        ? specializationResponses.stream().filter(
                                                spec -> spec.id().equals(clinic.getSpecializationId()))
                                                .findFirst()
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                        "not found specialization with id: "
                                                                + clinic.getSpecializationId()))
                                        : null))
                        .toList());
    }

    public PageResponse<ClinicResponse> searchAllWithSpecialization(
        String code, String name, Long specializationId,
        Pageable pageable) {

        Specification<Clinic> specification = ClinicSpecifications.haveCodeEqual(code)
                .or(ClinicSpecifications.haveNameLike(name))
                .or(ClinicSpecifications.haveSpecializationIdEqual(specializationId));
        var pageResult = clinicRepository.findAll(specification, pageable);

        List<SpecializationResponse> specializationResponses = specializationClient
                .findAllById(pageResult.stream().map(Clinic::getSpecializationId).toList());

        return PageResponse.fromPage(pageResult,
                pageResult.getContent().stream()
                        .map(clinic -> clinicMapper.convertClinicResponseFrom(clinic,
                                clinic.getSpecializationId() != null
                                        ? specializationResponses.stream().filter(
                                                spec -> spec.id().equals(clinic.getSpecializationId()))
                                                .findFirst()
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                        "not found specialization with id: "
                                                                + clinic.getSpecializationId()))
                                        : null))
                        .toList());
    }

    public ClinicResponse findWithSpecializationById(Long clinicId) {
        Clinic clinic = findById(clinicId);
        SpecializationResponse specializationResponse = 
            specializationClient.findById(clinic.getSpecializationId())
            .orElseThrow(()->new EntityNotFoundException(
                "not found specialization with id: "
                        + clinic.getSpecializationId()));
        return ClinicResponse.builder()
            .id(clinic.getId())
            .name(clinic.getName())
            .code(clinic.getCode())
            .specialization(specializationResponse)
            .build();
    }

    public Clinic findById(Long clinicId) {
        return clinicRepository.findById(clinicId).orElseThrow(
                () -> new EntityNotFoundException("not found clinic with id: " + clinicId));
    }

    public List<Clinic> findAllById(List<Long> clinicIds) {
        return clinicRepository.findAllById(clinicIds);
    }

    public void deleteById(Long clinicId) {
        clinicRepository.deleteById(clinicId);
    }

    public void deleteAllById(List<Long> clinicIds) {
        clinicRepository.deleteAllById(clinicIds);
    }
}
