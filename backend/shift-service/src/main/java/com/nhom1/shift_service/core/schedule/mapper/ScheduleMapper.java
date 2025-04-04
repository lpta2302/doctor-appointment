package com.nhom1.shift_service.core.schedule.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.nhom1.shift_service.core.schedule.dto.ScheduleResponse;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;

@Component
public class ScheduleMapper {

    public ScheduleResponse convertScheduleResponseFrom(Schedule schedule,
            List<ShiftResponse> shiftResponse) {
        return ScheduleResponse.builder()
            .clinicId(schedule.getScheduleId().getClinicId())
            .specializationName(schedule.getSpecializationName())
            .clinicName(schedule.getClinicName())
            .appliedDate(schedule.getScheduleId().getAppliedDate())
            .specializationId( schedule.getSpecializationId())
            .shifts(shiftResponse)
            .build();
    }
}
