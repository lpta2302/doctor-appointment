package com.nhom1.shift_service.core.schedule.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.schedule.entity.ScheduleId;

public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId>{

    Page<Schedule> findAll(Specification<Schedule> specification, Pageable pageable);

    List<Schedule> findAll(Specification<Schedule> specification);
    
}
