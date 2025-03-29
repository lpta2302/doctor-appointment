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

    private final SpecializationClient specializationService;
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    public Long create(Clinic clinic) {

        specializationService.findById(clinic.getSpecializationId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "not found specialization with id: " + clinic.getSpecializationId()));

        return clinicRepository.save(clinic).getId();
    }

    public Long update(Long clinicId, Clinic clinic) {
        specializationService.findById(clinic.getSpecializationId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "not found specialization with id: " + clinic.getSpecializationId()));

        Clinic foundClinic = findById(clinicId);
        if (!foundClinic.getCode().equals(clinic.getCode())) {
            foundClinic.setCode(clinic.getCode());
        }
        foundClinic.setName(clinic.getName());
        foundClinic.setSpecializationId(clinic.getSpecializationId());

        return clinicRepository.save(foundClinic).getId();
    }

    public PageResponse<ClinicResponse> findAll(Pageable pageable) {
        var pageResult = clinicRepository.findAll(pageable);

        List<SpecializationResponse> specializationResponses = specializationService
                .findAllById(pageResult.stream().map(Clinic::getSpecializationId).toList());

        return PageResponse.fromPage(pageResult,
                pageResult.getContent().stream()
                        .map(clinic -> clinicMapper.convertClinicResponseFrom(clinic,
                                clinic.getSpecializationId() != null
                                        ? specializationResponses.stream().filter(
                                                spec -> spec.id() == clinic.getSpecializationId())
                                                .findFirst()
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                        "not found specialization with id: "
                                                                + clinic.getSpecializationId()))
                                        : null))
                        .toList());
    }

    public PageResponse<ClinicResponse> findAllByCodeOrName(String code, String name,
            Pageable pageable) {
        if (code == null && name == null) {
            throw new IllegalArgumentException("name and code can't be null");
        }

        Specification<Clinic> specification = ClinicSpecifications.haveCodeEqual(code)
                .or(ClinicSpecifications.haveNameLike(name));
        var pageResult = clinicRepository.findAll(specification, pageable);

        List<SpecializationResponse> specializationResponses = specializationService
                .findAllById(pageResult.stream().map(Clinic::getSpecializationId).toList());

        return PageResponse.fromPage(pageResult,
                pageResult.getContent().stream()
                        .map(clinic -> clinicMapper.convertClinicResponseFrom(clinic,
                                clinic.getSpecializationId() != null
                                        ? specializationResponses.stream().filter(
                                                spec -> spec.id() == clinic.getSpecializationId())
                                                .findFirst()
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                        "not found specialization with id: "
                                                                + clinic.getSpecializationId()))
                                        : null))
                        .toList());
    }

    public ClinicResponse findClinicWithSpecializationById(Long clinicId) {
        Clinic clinic = findById(clinicId);
        SpecializationResponse specializationResponse = 
            specializationService.findById(clinic.getSpecializationId())
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
