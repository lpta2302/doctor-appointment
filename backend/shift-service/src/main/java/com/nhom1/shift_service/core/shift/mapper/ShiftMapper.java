package com.nhom1.shift_service.core.shift.mapper;

import org.springframework.stereotype.Component;

import com.nhom1.shift_service.core.doctor.dto.DoctorResponse;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;
import com.nhom1.shift_service.core.shift.entity.Shift;

@Component
public class ShiftMapper {
    public ShiftResponse convertShiftResponseFrom(Shift shift, DoctorResponse doctor){
        return ShiftResponse.builder()
            .id(shift.getId())
            .doctor(doctor)
            .startTime(shift.getStartTime())
            .endTime(shift.getEndTime())
            .build();
    }

    public Shift convertShiftFrom(ShiftRequest request, DoctorResponse doctor){
        return Shift.builder()
            .doctorId(doctor.id())
            .doctorCode(doctor.code())
            .doctorFullname(doctor.fullname())
            .doctorPhoneNumber(doctor.phoneNumber())
            .startTime(request.startTime())
            .endTime(request.endTime())
            .build();
    }
}
