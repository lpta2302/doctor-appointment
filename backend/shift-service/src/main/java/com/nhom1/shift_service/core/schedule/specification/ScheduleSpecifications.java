package com.nhom1.shift_service.core.schedule.specification;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.data.jpa.domain.Specification;
import com.nhom1.shift_service.common.TypeCaster;
import com.nhom1.shift_service.core.schedule.entity.Schedule;

public class ScheduleSpecifications {
    private final static Map<String, Function<String, Specification<Schedule>>> specificationMap =
        new HashMap<>();

    static {
        specificationMap.put("clinic-id", ScheduleSpecifications::haveClinicIdEqual);
        specificationMap.put("applied-date", ScheduleSpecifications::haveAppliedDate);
    }
    
    public static Specification<Schedule> haveClinicIdEqual(String clinicId){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("scheduleId").get("clinicId"), 
                TypeCaster.castToNumber(
                    root.get("scheduleId").get("clinicId").getJavaType(), 
                    clinicId));
    }

    public static Specification<Schedule> haveAppliedDate(String appliedDate){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("scheduleId").get("clinicId"), 
                TypeCaster.castToLocalDate(
                    root.get("scheduleId").get("clinicId").getJavaType(), 
                    appliedDate));
    }

    public static Specification<Schedule> createSearchSpecification(Map<String,String> params){
        Specification<Schedule> finalSpecification = Specification.where(null);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
                String value = entry.getValue();
                Function<String, Specification<Schedule>> specificationMethod =
                    specificationMap.get(key);
                
                if (specificationMethod != null && value != null) {
                    finalSpecification = finalSpecification.and(
                        specificationMethod.apply(value)
                    );
                }
        }

        return finalSpecification;
    }
}
