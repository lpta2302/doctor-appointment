package com.nhom1.shift_service.core.shift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nhom1.shift_service.core.shift.entity.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    
}
