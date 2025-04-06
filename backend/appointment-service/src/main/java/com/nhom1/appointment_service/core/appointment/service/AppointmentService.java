package com.nhom1.appointment_service.core.appointment.service;

import static com.nhom1.appointment_service.core.appointment.enums.AppointmentStatus.ACCEPTED;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.nhom1.appointment_service.core.appointment.dto.AppointmentRequest;
import com.nhom1.appointment_service.core.appointment.dto.AvailableTime;
import com.nhom1.appointment_service.core.appointment.entity.Appointment;
import com.nhom1.appointment_service.core.appointment.repository.AppointmentRepository;
import com.nhom1.appointment_service.core.patient.entity.Patient;
import com.nhom1.appointment_service.core.patient.service.PatientService;
import com.nhom1.appointment_service.core.schedule.client.ScheduleClient;
import com.nhom1.appointment_service.core.schedule.dto.ScheduleResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    @Value("${application.config.appointment-time}")
    private int APPOINTMENT_TIME;

    private final AppointmentRepository appointmentRepository;
    
    private final ScheduleClient scheduleClient;

    private final PatientService patientService;

    public Long create(AppointmentRequest request){
        Patient patient = request.patient();
        
        if (request.isOldPatient()) {
            patient = 
                patientService.findByPhoneNumber(request.patient().getPhoneNumber());
        }

        ScheduleResponse scheduleResponse = 
            scheduleClient.findScheduleTimeById(request.clinicId(), request.appointmentDate().toString())
            .orElseThrow(()-> new EntityNotFoundException(
                "not found schedule of date: " +
                request.appointmentDate()
            ));
        

        validateAppointmentTime(
            request.clinicId(),
            request.appointmentDate(),
            request.appointmentTime(),
            scheduleResponse.shiftsTime()
        );

        Appointment appointment = Appointment.builder()
            .appointmentDate(request.appointmentDate())
            .appointmentTime(request.appointmentTime())
            .clinicId(request.clinicId())
            .specializationId(scheduleResponse.specializationId())
            .clinicName(scheduleResponse.clinicName())
            .specializationName(scheduleResponse.specializationName())
            .status(ACCEPTED)
            .build();

        appointment = appointmentRepository.save(appointment);
        
        patient.addAppointment(appointment);
        patientService.save(patient);
        

        return appointment.getId();
    }

    public List<AvailableTime> findAllAvailableTime(Long clinicId, LocalDate appointmentDate) {
        ScheduleResponse scheduleResponse = 
            scheduleClient.findScheduleTimeById(clinicId, appointmentDate.toString())
            .orElseThrow(() -> new EntityNotFoundException(
                "Not found schedule of date: " + appointmentDate
            ));
    
        Map<LocalTime, LocalTime> shiftsTime = scheduleResponse.shiftsTime();
        List<LocalTime> appointmentsTime = 
            new ArrayList<>(appointmentRepository.findAllAppointmentTime(clinicId, appointmentDate));
    
        List<AvailableTime> availableTimes = new ArrayList<>();
    
        shiftsTime.forEach((startTime, endTime) -> {
            LocalTime currentTime = startTime;
            while (currentTime.isBefore(endTime)) {
                boolean isBooked = appointmentsTime.contains(currentTime);
    
                availableTimes.add(
                    AvailableTime.builder()
                        .time(currentTime)
                        .isAvailable(!isBooked)
                        .build()
                );
    
                currentTime = currentTime.plusMinutes(APPOINTMENT_TIME);
            }
        });
    
        return availableTimes;
    }
    

    public void validateAppointmentTime(
        Long clinicId,
        LocalDate appliedDate,
        LocalTime appointmentTime, 
        Map<LocalTime, LocalTime> scheduleTime){
        scheduleTime.entrySet().stream()
            .filter(entry->
                !appointmentTime.isAfter(entry.getValue())
                && !appointmentTime.isBefore(entry.getKey()))
            .findFirst()
            .orElseThrow(
                ()-> new IllegalArgumentException(
                "invalid appointment time"
            ));

        boolean isOverlapAppointment = appointmentRepository
            .existsByAppointmentTimeAndClinicIdAndAppointmentDate(appointmentTime, clinicId, appliedDate);

        if (isOverlapAppointment) {
            throw new IllegalArgumentException(
                "invalid appointment time"
            );
        }
    }

}
