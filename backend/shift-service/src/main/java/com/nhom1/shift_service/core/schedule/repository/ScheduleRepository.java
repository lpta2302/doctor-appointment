package com.nhom1.shift_service.core.schedule.repository;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.schedule.entity.ScheduleId;

public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId>{

    Page<Schedule> findAll(Specification<Schedule> specification, Pageable pageable);
    
}
