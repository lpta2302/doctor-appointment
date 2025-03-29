package com.nhom1.shift_service.core.schedule.entity;

import java.time.LocalDate;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
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
    @Id
    private LocalDate appliedDate;

    @Id
    private Long clinicId;
}
