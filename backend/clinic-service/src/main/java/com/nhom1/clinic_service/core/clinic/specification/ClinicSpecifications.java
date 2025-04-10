package com.nhom1.clinic_service.core.clinic.specification;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import com.nhom1.clinic_service.common.TypeCaster;
import com.nhom1.clinic_service.core.clinic.entity.Clinic;

public class ClinicSpecifications {
    private final static Map<String, Function<String, Specification<Clinic>>> specificationMap =
        new HashMap<>();

    static {
        specificationMap.put("id", ClinicSpecifications::haveIdEqual);
        specificationMap.put("name", ClinicSpecifications::haveNameLike);
        specificationMap.put("code", ClinicSpecifications::haveCodeEqual);
        specificationMap.put("specialization", ClinicSpecifications::haveSpecializationIdEqual);
    }
    
    public static Specification<Clinic> haveIdEqual(String id){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("id"), 
                TypeCaster.castToNumber(root.get("id").getJavaType(), id));
    }

    public static Specification<Clinic> haveNameLike(String name){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")), 
                "%" + name.toLowerCase() + "%");
    }

    public static Specification<Clinic> haveCodeEqual(String code){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                criteriaBuilder.lower(root.get("code")), 
                code.toLowerCase());
    }

    public static Specification<Clinic> createSearchSpecification(Map<String,String> params){
        Specification<Clinic> finalSpecification = Specification.where(null);

        params.entrySet().forEach(
            entry->{
                String key = entry.getKey();
                String value = entry.getValue();
                Function<String, Specification<Clinic>> specificationMethod =
                    specificationMap.get(key);
                
                if (specificationMethod != null && value != null) {
                    finalSpecification.and(
                        specificationMethod.apply(value)
                    );
                }
            }
        );

        return finalSpecification;
    }

    public static Specification<Clinic> haveSpecializationIdEqual(String specializationId) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("specializationId"), 
                TypeCaster.castToNumber(
                    root.get("specializationId").getJavaType(), specializationId));
    }

    public static Specification<Clinic> haveSpecializationIdEqual(Long specializationId) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("specializationId"), 
                specializationId);
    }
}
