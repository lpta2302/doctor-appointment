package com.nhom1.shift_service.core.shift.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.shift.entity.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findAllBySchedule(Schedule schedule);

    @Query("""
                select sh
                from Shift sh
                where sh.schedule in :schedules
            """)
    List<Shift> findAllBySchedules(List<Schedule> schedules);

    void deleteByDoctorId(Long id);

    @Modifying
    @Query("""
                update Shift s
                set s.doctorFullname = :fullname,
                s.doctorPhoneNumber = :phoneNumber,
                s.doctorCode = :code
                where s.doctorId = :id
            """)
    void updateDoctorChange(Long id, String code, String fullname, String phoneNumber);

}
