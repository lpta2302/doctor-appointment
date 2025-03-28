package com.nhom1.shift_service.common;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TypeCaster {
    public static Object castToNumber(Class<?> fieldType, String value) {
        try {
            if (fieldType.isAssignableFrom(Double.class)) {
                return Double.valueOf(value);
            } else if (fieldType.isAssignableFrom(Float.class)) {
                return Float.valueOf(value);
            } else if (fieldType.isAssignableFrom(Long.class)) {
                return Long.valueOf(value);
            } else if (fieldType.isAssignableFrom(Integer.class)) {
                return Integer.valueOf(value);
            }
        } catch (NumberFormatException e) {
            throw e;
        }
        return null;
    }
    public static Object castToLocalDate(Class<?> fieldType, String value) {
        try {
            if (fieldType.isAssignableFrom(LocalDate.class)) {
                return LocalDate.parse(value);
            }
        } catch (DateTimeParseException e) {
            throw e;
        }
        return null;
    }
}
