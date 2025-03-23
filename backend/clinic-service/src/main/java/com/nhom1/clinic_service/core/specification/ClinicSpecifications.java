package com.nhom1.clinic_service.core.specification;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.data.jpa.domain.Specification;
import com.nhom1.clinic_service.core.entity.Clinic;

public class ClinicSpecifications {
    private final static Map<String, Function<String, Specification<Clinic>>> specificationMap =
        new HashMap<>();

    static {
        specificationMap.put("id", ClinicSpecifications::haveIdEqual);
    }
    
    public static Specification<Clinic> haveIdEqual(String clinic_id){
        return (root, _, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("id"), clinic_id);
    }

    
}
