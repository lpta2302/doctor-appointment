package com.nhom1.shift_service.core.shift.mapper;

import org.springframework.stereotype.Component;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;
import com.nhom1.shift_service.core.shift.entity.Shift;

@Component
public class ShiftMapper {
    public ShiftResponse convertShiftResponseFrom(Shift shift){
        return ShiftResponse.builder()
            .id(shift.getId())
            .startTime(shift.getStartTime())
            .endTime(shift.getEndTime())
            .build();
    }
}
