package com.nhom1.doctor_service.core.specialization.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nhom1.doctor_service.common.PageResponse;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;
import com.nhom1.doctor_service.core.specialization.repository.SpecializationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    public Long create(Specialization specialization){
        return specializationRepository.save(specialization).getId();
    }

    public Long update(Long specializationId ,Specialization specialization){
        Specialization foundSpecialization = findById(specializationId);

        foundSpecialization.setName(specialization.getName());
        foundSpecialization.setCode(specialization.getCode());
        foundSpecialization.setPrice(specialization.getPrice());

        return specializationRepository.save(specialization).getId();
    }

    public Specialization findById(Long id){
        return specializationRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException(
                "Not found specialization with id: "+id));
    }

    public List<Specialization> findAllByIds(List<Long> ids){
        return specializationRepository.findAllById(ids);
    }

    public PageResponse<Specialization> findAll(Pageable pageable){
        return PageResponse.fromPage(
            specializationRepository.findAll(pageable)
        );
    }

    public PageResponse<Specialization> findByNameOrCode(String code, String name, Pageable pageable){
        if (code == null && name == null) {
            throw new IllegalArgumentException("name and code can't be null");
        }
        
        return PageResponse.fromPage(
            specializationRepository.findAllByCodeOrNameContaining(code, name.toLowerCase(), pageable)
        );
    }

    public void deleteById(Long specializationId) {
        specializationRepository.deleteById(specializationId);
    }

    public void deleteAllById(List<Long> specializationIds) {
        specializationRepository.deleteAllById(specializationIds);
    }

}
