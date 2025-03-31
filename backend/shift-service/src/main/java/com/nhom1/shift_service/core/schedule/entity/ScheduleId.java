package com.nhom1.shift_service.core.schedule.entity;

import java.time.LocalDate;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ScheduleId {
    private LocalDate appliedDate;
    private Long clinicId;
}
