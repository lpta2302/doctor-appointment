package com.nhom1.shift_service.core.schedule.dto;

import java.time.LocalDate;
import java.util.List;
import com.nhom1.shift_service.core.shift.dto.ShiftRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ScheduleRequest(
    @FutureOrPresent(message = "applied date must after now")
    LocalDate appliedDate,
    
    @NotNull(message = "clinic id can't be null")
    @PositiveOrZero(message = "clinic id must be equal or greater than 0")
    Long clinicId,
    
    @Valid
    List<ShiftRequest> shiftRequests
) {}
