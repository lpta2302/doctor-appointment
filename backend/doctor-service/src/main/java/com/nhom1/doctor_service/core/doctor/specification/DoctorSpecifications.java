package com.nhom1.doctor_service.core.doctor.specification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        specificationMap.put("ids", DoctorSpecifications::haveIdEqualIn);
        specificationMap.put("name", DoctorSpecifications::haveNameLike);
        specificationMap.put("code", DoctorSpecifications::haveCodeEqual);
        specificationMap.put("specification", DoctorSpecifications::haveSpecialization);
    }
    
    public static Specification<Doctor> haveIdEqual(String id){
        return (root, _, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("id"), 
                TypeCaster.castToNumber(root.get("id").getJavaType(), id));
    }

    public static Specification<Doctor> haveIdEqualIn(String idsString){
        return (root, _, _) -> {
            List<Object> ids = 
                Arrays
                .stream(idsString.split(","))
                .map(id->TypeCaster.castToNumber(root.get("id").getJavaType(), id))
                .toList();

            return root.get("id").in(ids);
        };
    }

    public static Specification<Doctor> haveNameLike(String name){
        return (root, _, criteriaBuilder) -> 
            criteriaBuilder.like(
                criteriaBuilder.concat(
                    criteriaBuilder.concat(
                        root.get("firstName"), 
                        criteriaBuilder.literal(" ")),
                    root.get("lastName")), 
                name+"%");
    }

    public static Specification<Doctor> haveCodeEqual(String code){
        return (root, _, criteriaBuilder) -> 
            criteriaBuilder.equal(
                root.get("code"), 
                code);
    }

    public static Specification<Doctor> haveSpecialization(String specificationId){
        return (root, _, criteriaBuilder) ->{ 
            Join<Object, Object> specializations = root.join("specializations");
            
            return criteriaBuilder.equal(
                specializations.get("id"), 
                specificationId);
        };
    }

    public static Specification<Doctor> createSearchSpecification(Map<String,String> params){
        Specification<Doctor> finalSpecification = Specification.where(null);

        params.entrySet().forEach(
            entry->{
                String key = entry.getKey();
                String value = entry.getValue();
                Function<String, Specification<Doctor>> specificationMethod =
                    specificationMap.get(key);
                
                if (specificationMethod != null) {
                    finalSpecification.and(
                        specificationMethod.apply(value)
                    );
                }
            }
        );

        return finalSpecification;
    }
}
