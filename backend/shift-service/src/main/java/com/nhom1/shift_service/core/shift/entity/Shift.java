package com.nhom1.shift_service.core.shift.entity;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.shift_service.core.schedule.entity.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue
    private Long id;
    
    @Version
    @JsonIgnore
    private Long version;

    @NotNull(message = "doctor id can't be null")
    @PositiveOrZero(message = "doctor id must be equal or greater than 0")
    private Long doctorId;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Schedule schedule;
}
