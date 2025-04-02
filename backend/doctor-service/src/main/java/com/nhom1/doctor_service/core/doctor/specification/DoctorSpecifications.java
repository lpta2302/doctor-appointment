package com.nhom1.doctor_service.core.doctor.specification;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.data.jpa.domain.Specification;
import com.nhom1.doctor_service.common.TypeCaster;
import com.nhom1.doctor_service.core.doctor.entity.Doctor;
import jakarta.persistence.criteria.Join;

public class DoctorSpecifications {
    private final static Map<String, Function<String, Specification<Doctor>>> specificationMap =
        new HashMap<>();

    static {
        specificationMap.put("id", DoctorSpecifications::haveIdEqual);
        specificationMap.put("name", DoctorSpecifications::haveNameLike);
        specificationMap.put("code", DoctorSpecifications::haveCodeEqual);
        specificationMap.put("specialization", DoctorSpecifications::haveSpecialization);
    }
    
    public static Specification<Doctor> haveIdEqual(String id){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("id"), 
                TypeCaster.castToNumber(root.get("id").getJavaType(), id));
    }

    public static Specification<Doctor> haveNameLike(String name){
        Specification<Doctor> checkLastName = (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("lastName")), 
                name.toLowerCase()+"%");

        Specification<Doctor> checkFullname = (root, query, criteriaBuilder) -> 
        criteriaBuilder.like(
            criteriaBuilder.concat(
                criteriaBuilder.concat(
                    criteriaBuilder.lower(root.get("firstName")), 
                    criteriaBuilder.literal(" ")),
                criteriaBuilder.lower(root.get("lastName"))), 
            "%" + name.toLowerCase() + "%");

        return checkLastName.or(checkFullname);
    }

    public static Specification<Doctor> haveCodeEqual(String code){
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(
                criteriaBuilder.lower(root.get("code")), 
                code.toLowerCase());
    }

    public static Specification<Doctor> haveSpecialization(String specificationId){
        return (root, query, criteriaBuilder) ->{ 
            Join<Object, Object> specializations = root.join("specializations");
            
            return criteriaBuilder.equal(
                specializations.get("id"), 
                specificationId);
        };
    }

    public static Specification<Doctor> createSearchSpecification(Map<String,String> params){
        Specification<Doctor> finalSpecification = Specification.where(null);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
                String value = entry.getValue();
                Function<String, Specification<Doctor>> specificationMethod =
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
