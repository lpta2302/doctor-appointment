package com.nhom1.shift_service.core.schedule.dto;

import java.time.LocalDate;
import java.util.List;
import com.nhom1.shift_service.core.shift.dto.ShiftResponse;
import lombok.Builder;

@Builder
public record ScheduleResponse(
    Long clinicId,
    LocalDate appliedDate,
    Long specializationId,
    List<ShiftResponse> shifts
) {}
