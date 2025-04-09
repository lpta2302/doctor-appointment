package com.nhom1.appointment_service.core.appointment.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.nhom1.appointment_service.core.appointment.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
                select a.appointmentTime
                from Appointment a
                where a.clinicId = :clinicId and a.appointmentDate = :appointmentDate
                order by a.appointmentTime asc
            """)
    List<LocalTime> findAllAppointmentTime(Long clinicId, LocalDate appointmentDate);

    boolean existsByAppointmentTimeAndClinicIdAndAppointmentDate(LocalTime appointmentTime,
            Long clinicId, LocalDate appointmentDate);

    @Modifying
    @Query("""
               update Appointment a
               set a.specializationName = :name
               where a.specializationId = :id
               and a.appointmentDate > CURRENT_DATE
            """)
    void updateSpecializationDetailOfAppointments(Long id, String name);

    @Modifying
    @Query("""
               update Appointment a
               set a.clinicName = :name
               where a.clinicId = :id
               and a.appointmentDate > CURRENT_DATE
            """)
    void updateClinicChange(Long id, String name);

    @Modifying
    @Query("""
               update Appointment a
               set a.status = 'CANCELLED'
               where a.clinicId = :id
               and a.appointmentDate > CURRENT_DATE
               or (a.appointmentDate = CURRENT_DATE and a.appointmentTime > CURRENT_TIME)
            """)
    void cancelByClinicId(Long id);

    @Modifying
    @Query("""
               update Appointment a
               set a.status = 'CANCELLED'
               where a.clinicId = :clinicId
               and a.appointmentDate = :appliedDate
               and a.appointmentTime >= :oldStartTime
               and a.appointmentTime <= :oldEndTime
            """)
    void cancelByShiftTime(LocalTime oldStartTime, LocalTime oldEndTime, LocalDate appliedDate, Long clinicId);

    @Modifying
    @Query("""
        update Appointment a
        set a.status = 'CANCELLED'
        where a.clinicId = :clinicId
        and a.appointmentDate = :appliedDate
        and a.appointmentTime >= :oldStartTime
        and a.appointmentTime <= :oldEndTime
        and (a.appointmentTime < :newStartTime or a.appointmentTime > :newEndTime)
    """)
    void cancelAllByNewShiftTime(LocalTime oldStartTime, LocalTime oldEndTime, LocalTime newStartTime,
            LocalTime newEndTime, LocalDate appliedDate, Long clinicId);

    @Modifying
    @Query("""
               update Appointment a
               set a.status = 'CANCELLED'
               where a.specializationId = :id
               and a.appointmentDate > CURRENT_DATE
               or (a.appointmentDate = CURRENT_DATE and a.appointmentTime > CURRENT_TIME)
            """)
    void cancelAllBySpecializationId(Long id);

    @Modifying
    @Query("""
               update Appointment a
               set a.status = 'CANCELLED'
               where a.clinicId = :clinicId
               and a.appointmentDate = :appliedDate
               and a.appointmentDate > CURRENT_DATE
               or (a.appointmentDate = CURRENT_DATE and a.appointmentTime > CURRENT_TIME)
            """)
    void cancelBySchedule(Long clinicId, LocalDate appliedDate);

}
