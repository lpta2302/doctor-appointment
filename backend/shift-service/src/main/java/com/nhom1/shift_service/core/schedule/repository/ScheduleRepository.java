package com.nhom1.shift_service.core.schedule.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.schedule.entity.ScheduleId;

public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {

      Page<Schedule> findAll(Specification<Schedule> specification, Pageable pageable);

      List<Schedule> findAll(Specification<Schedule> specification);

      @Modifying
      @Query("""
                     update Schedule s
                     set s.specializationId = null, s.specializationName = null
                     where s.specializationId = :id
                  """)
      void deleteSpecializationDetailOfSchedules(Long id);

      @Modifying
      @Query("""
                  update Schedule s
                  set s.specializationName = :name
                  where s.specializationId = :id
                  """)
      void updateSpecializationDetailOfSchedules(Long id, String name);

      @Modifying
      @Query("""
                     delete Schedule s
                  where s.scheduleId.clinicId = :id
                  """)
      void deleteByClinicId(Long id);

      @Modifying
      @Query("""
                      update Schedule s
                      set s.clinicName = :name
                      where s.scheduleId.clinicId = :id and
                      s.scheduleId.appliedDate > CURRENT_DATE
                  """)
      void updateClinicChange(Long id, String name);

      @Modifying
      @Query("DELETE FROM Schedule s WHERE s.scheduleId.clinicId = :clinicId AND s.scheduleId.appliedDate = :appliedDate")
      void deleteByClinicIdAndAppliedDate(Long clinicId, LocalDate appliedDate);


}
