package com.nhom1.clinic_service.core.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.nhom1.clinic_service.common.PageResponse;
import com.nhom1.clinic_service.core.entity.Clinic;
import com.nhom1.clinic_service.core.repository.ClinicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;

    public Long create(Clinic clinic){
        return clinicRepository.save(clinic).getId();
    }

    public Long update(Long clinicId ,Clinic clinic){
        Clinic foundClinic = 
            findById(clinicId);

        return clinicRepository.save(foundClinic).getId();
    }

    public PageResponse<Clinic> findAll(Pageable pageable){
        return PageResponse.fromPage(
            clinicRepository.findAll(pageable)
        );
    }

    public PageResponse<Clinic> findAllByCodeOrName(String code, String name, Pageable pageable){
        if (code == null && name == null) {
            throw new IllegalArgumentException("name and code can't be null");
        }

        return PageResponse.fromPage(
            clinicRepository.findAllByCodeOrNameContaining(code, name.toLowerCase(), pageable)
        );
    }

    public Clinic findById(Long clinicId){
        return clinicRepository.findById(clinicId)
            .orElseThrow(()->new EntityNotFoundException(
                "not found clinic with id: "+clinicId));
    }

    public PageResponse<Clinic> findAllById(List<Long> clinicIds, Pageable pageable) {
        Page<Clinic> pageResult = clinicRepository.findAllById(clinicIds, pageable);
        return PageResponse.fromPage(pageResult);
    }
}
