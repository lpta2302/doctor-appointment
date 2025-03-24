package com.nhom1.clinic_service.common;

@SuppressWarnings("unchecked")
public class TypeCaster {
    @SuppressWarnings("rawtypes")
    public static Object castToNumber(Class fieldType, String value){
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Float.class)) {
            return Float.valueOf(value);
        } else if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        }
        return null;
    }
}
