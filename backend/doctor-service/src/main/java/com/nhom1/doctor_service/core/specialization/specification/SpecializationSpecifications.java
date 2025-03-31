package com.nhom1.doctor_service.core.specialization.specification;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.data.jpa.domain.Specification;
import com.nhom1.doctor_service.common.TypeCaster;
import com.nhom1.doctor_service.core.specialization.entity.Specialization;

public class SpecializationSpecifications {
    private final static Map<String, Function<String, Specification<Specialization>>> specificationMap =
        new HashMap<>();

    static {
        specificationMap.put("id", SpecializationSpecifications::haveIdEqual);
        specificationMap.put("code", SpecializationSpecifications::haveCodeEqual);
        specificationMap.put("name", SpecializationSpecifications::haveNameLike);
    }
    
    public static Specification<Specialization> haveIdEqual(String id){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("id"), 
                TypeCaster.castToNumber(root.get("id").getJavaType(), id));
    }

    public static Specification<Specialization> haveNameLike(String name){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")), 
                "%"+name.toLowerCase()+"%");
    }

    public static Specification<Specialization> haveCodeEqual(String code){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                criteriaBuilder.lower(root.get("code")), 
                code.toLowerCase());
    }

    public static Specification<Specialization> createSearchSpecification(Map<String,String> params){
        Specification<Specialization> finalSpecification = Specification.where(null);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
                String value = entry.getValue();
                Function<String, Specification<Specialization>> specificationMethod =
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
