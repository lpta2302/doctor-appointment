package com.nhom1.shift_service.core.schedule.entity;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom1.shift_service.core.shift.entity.Shift;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
@Table(name = "schedules")
public class Schedule {
    @EmbeddedId
    private ScheduleId scheduleId;
    
    @Version
    @JsonIgnore
    private Long version;

    @PositiveOrZero(message = "specialization id must be equal or greater than 0")
    private Long specializationId;

    @Column(length=200)
    @Size(min=1, max=200, message="clinic name has 1-200 characters")
    private String clinicName;

    @Column(length=200)
    @Size(min=1, max=200, message="specialization name has 1-200 characters")
    private String specializationName;

    @OneToMany(mappedBy = "schedule", cascade = {REMOVE, PERSIST}, orphanRemoval = true, fetch = EAGER)
    @OrderBy("startTime ASC")
    private List<Shift> shifts;

    @Schema(accessMode = READ_ONLY)
    public Shift getCurrentShift(){
        return shifts.stream()
            .filter(shift -> 
                shift.getStartTime().isBefore(LocalTime.now()) &&
                shift.getEndTime().isAfter(LocalTime.now()))
            .findFirst()
            .orElse(null);
    }

    public Schedule(LocalDate appliedDate, Long clinicId){
        scheduleId = new ScheduleId();
        scheduleId.setAppliedDate(appliedDate);
        scheduleId.setClinicId(clinicId);
    }

    public void setShifts(List<Shift> shifts){
        if (this.shifts == null) {
            this.shifts = new ArrayList<>();
        } else {
            this.shifts.clear();
        }

        shifts.forEach(shift->{
            this.shifts.add(shift);
            shift.setSchedule(this);
        });

    }

    public void addShift(Shift newShift) {
        if (shifts == null) {
            shifts = new ArrayList<>();
        }

        shifts.add(newShift);
        newShift.setSchedule(this);
    }

    public void insertShift(int index, Shift newShift) {
        if (shifts == null) {
            shifts = new ArrayList<>();
        }

        shifts.add(index, newShift);
        newShift.setSchedule(this);
    }
}
