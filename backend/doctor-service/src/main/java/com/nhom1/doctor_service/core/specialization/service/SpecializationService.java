package com.nhom1.doctor_service.core.specialization.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nhom1.doctor_service.common.PageResponse;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;
import com.nhom1.doctor_service.core.specialization.repository.SpecializationRepository;
import com.nhom1.doctor_service.core.specialization.specification.SpecializationSpecifications;
import com.nhom1.doctor_service.kafka.SpecializationInfo;
import com.nhom1.doctor_service.kafka.SpecializationProducer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecializationService {

    private final SpecializationProducer specializationProducer;

    private final SpecializationRepository specializationRepository;

    public Long create(Specialization specialization){
        return specializationRepository.save(specialization).getId();
    }

    public Long update(Long specializationId ,Specialization specialization){
        Specialization foundSpecialization = findById(specializationId);

        if (!foundSpecialization.getCode().equals(specialization.getCode())) {
            foundSpecialization.setCode(specialization.getCode());
        }
        foundSpecialization.setName(specialization.getName());
        foundSpecialization.setPrice(specialization.getPrice());

        specializationProducer.sendUpdatedSpecializationMessage(
            new SpecializationInfo(specializationId, specialization.getName())
        );

        return specializationRepository.save(foundSpecialization).getId();
    }

    public boolean checkById(Long id){
        return specializationRepository.existsById(id);
    }

    public Specialization findById(Long id){
        return specializationRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException(
                "Not found specialization with id: "+id));
    }

    public List<Specialization> findAllById(List<Long> ids){
        return specializationRepository.findAllById(ids);
    }

    public PageResponse<Specialization> findAll(Pageable pageable){
        return PageResponse.fromPage(
            specializationRepository.findAll(pageable)
        );
    }

    public void deleteById(Long specializationId) {
        Specialization specialization =
            findById(specializationId);
        
        specialization.getDoctors().forEach(
            doctor->doctor.getSpecializations().clear()
        );

        specializationRepository.deleteById(specializationId);
        specializationProducer.sendDeletedSpecializationMessage(
            new SpecializationInfo(specializationId, specialization.getName())
        );
    }

    public void deleteAllById(List<Long> specializationIds) {
        specializationRepository.deleteAllById(specializationIds);
    }

    public PageResponse<Specialization> findAllByCodeOrNameLike(String code, String name, Pageable pageable) {
        Specification<Specialization> specification = 
            SpecializationSpecifications.haveCodeEqual(code)
                .or(SpecializationSpecifications.haveNameLike(name));
        
        Page<Specialization> pageResult = specializationRepository.findAll(specification, pageable); 
        return PageResponse.fromPage(pageResult);
    }

}
