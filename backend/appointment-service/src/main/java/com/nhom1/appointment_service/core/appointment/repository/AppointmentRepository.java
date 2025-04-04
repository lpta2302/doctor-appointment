package com.nhom1.appointment_service.core.appointment.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nhom1.appointment_service.core.appointment.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    @Query("""
        select a.appointmentTime
        from Appointment a
        where a.clinicId = :clinicId and a.appointmentDate = :appointmentDate
        order by a.appointmentTime asc   
    """)
    List<LocalTime> findAllAppointmentTime(Long clinicId, LocalDate appointmentDate);

    boolean existsByAppointmentTimeAndClinicIdAndAppointmentDate(
        LocalTime appointmentTime,    
        Long clinicId, 
        LocalDate appointmentDate);
    
}
