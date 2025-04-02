package com.nhom1.shift_service.core.schedule.specification;

import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;
import com.nhom1.shift_service.common.TypeCaster;
import com.nhom1.shift_service.core.schedule.entity.Schedule;

public class ScheduleSpecifications {
    public static Specification<Schedule> haveClinicIdEqual(String clinicId){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("scheduleId").get("clinicId"), 
                TypeCaster.castToNumber(
                    root.get("scheduleId").get("clinicId").getJavaType(), 
                    clinicId));
    }

    public static Specification<Schedule> haveSpecializationIdEqual(Long specializationId){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("specializationId"), 
                    specializationId);
    }

    public static Specification<Schedule> haveAppliedDate(LocalDate appliedDate){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("scheduleId").get("appliedDate"),  
                    appliedDate);
    }
}
