package com.nhom1.clinic_service.kafka.specialization;

import static java.lang.String.format;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.nhom1.clinic_service.core.clinic.repository.ClinicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecializationConsumer {

    private final ClinicRepository clinicRepository;

    @KafkaListener(topics = "specialization-deleted")
    @Transactional
    public void consumeSpecializationDeleted(SpecializationInfo info){
        log.info(format("Consuming the message from specialization-deleted Topic:: %s", info.toString()));
        clinicRepository.deleteSpecializationDetailOfClinics(info.id());
    }

    @KafkaListener(topics = "specialization-updated")
    @Transactional
    public void consumeSpecializationUpdated(SpecializationInfo info){
        log.info(format("Consuming the message from specialization-updated Topic:: %s", info.toString()));
        clinicRepository.updateSpecializationDetailOfClinics(info.id(), info.name());
    }
}
