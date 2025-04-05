package com.nhom1.clinic_service.core.clinic.service;

import java.util.List;
import java.util.Objects;
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
import com.nhom1.clinic_service.kafka.clinic.ClinicProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final SpecializationClient specializationClient;
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    private final ClinicProducer clinicProducer;

    public Long create(Clinic clinic) {
        SpecializationResponse specialization;
        if (clinic.getSpecializationId()!= null) {
                specialization = specializationClient.findById(clinic.getSpecializationId())
                .orElseThrow(()->new EntityNotFoundException(
                        "not found specialization with id: "+
                        clinic.getSpecializationId()));
                clinic.setSpecializationName(specialization.name());
        }

        
        return clinicRepository.save(clinic).getId();
    }

    public Long update(Long clinicId, Clinic clinic) {
        Clinic foundClinic = findById(clinicId);
            
        if (!foundClinic.getCode().equals(clinic.getCode())) {
                foundClinic.setCode(clinic.getCode());
        }

        if (    
                foundClinic.getSpecializationId() == null ||
                !foundClinic.getSpecializationId().equals(clinic.getSpecializationId())
        ) {
                SpecializationResponse specialization;
                
                if (clinic.getSpecializationId()!= null) {
                        specialization = specializationClient.findById(clinic.getSpecializationId())
                        .orElseThrow(()->new EntityNotFoundException(
                                "not found specialization with id: "+
                                clinic.getSpecializationId()));
                        foundClinic.setSpecializationName(specialization.name());
                }
                foundClinic.setSpecializationId(clinic.getSpecializationId());
        } 
        foundClinic.setName(clinic.getName());
        clinicProducer.sendUpdatedClinicMessage(
                clinicMapper.convertClinicInfoFrom(foundClinic)
        );

        return clinicRepository.save(foundClinic).getId();
    }

    public PageResponse<ClinicResponse> findAllWithSpecialization(Pageable pageable) {
        var pageResult = clinicRepository.findAll(pageable);

        List<SpecializationResponse> specializationResponses = specializationClient
                .findAllById(pageResult.stream()
                .map(Clinic::getSpecializationId)
                .filter(Objects::nonNull)
                .toList()
        );

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
                .findAllById(
                        pageResult.stream()
                        .map(Clinic::getSpecializationId)
                        .filter(Objects::nonNull)
                        .toList()
                );

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
        SpecializationResponse specializationResponse = null;

        if (clinic.getSpecializationId() != null) {
                specializationResponse = specializationClient.findById(clinic.getSpecializationId())
                .orElseThrow(()->new EntityNotFoundException(
                        "not found specialization with id: "
                                + clinic.getSpecializationId()));
        }
        return clinicMapper.convertClinicResponseFrom(clinic, specializationResponse);
    }

    public Clinic findById(Long clinicId) {
        return clinicRepository.findById(clinicId).orElseThrow(
                () -> new EntityNotFoundException("not found clinic with id: " + clinicId));
    }

    public List<Clinic> findAllById(List<Long> clinicIds) {
        return clinicRepository.findAllById(clinicIds);
    }

    public void deleteById(Long clinicId) {
        Clinic clinic = findById(clinicId);
        clinicRepository.deleteById(clinicId);
        clinicProducer.sendDeletedSpecializationMessage(
                clinicMapper.convertClinicInfoFrom(clinic)
        );
    }

    public void deleteAllById(List<Long> clinicIds) {
        clinicRepository.deleteAllById(clinicIds);
    }
}
