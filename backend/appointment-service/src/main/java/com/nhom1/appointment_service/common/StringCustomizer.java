package com.nhom1.appointment_service.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StringCustomizer {
    public enum APPROXIMATE_COMPARATOR{
        START_ONLY, 
        END_ONLY, 
        BOTH;
    }

    private static Map<APPROXIMATE_COMPARATOR, Function<String, String>> APPROXIMATE_COMPARATOR_METHOD = new HashMap<>();
    static {
        APPROXIMATE_COMPARATOR_METHOD.put(APPROXIMATE_COMPARATOR.START_ONLY, (raw)->"%"+raw);
        APPROXIMATE_COMPARATOR_METHOD.put(APPROXIMATE_COMPARATOR.END_ONLY, (raw)->raw+"%");
        APPROXIMATE_COMPARATOR_METHOD.put(APPROXIMATE_COMPARATOR.BOTH, (raw)->"%"+raw+"%");
    }

    public static String convertApproximateCompare(String raw ,APPROXIMATE_COMPARATOR compareType){
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        } else {
            return APPROXIMATE_COMPARATOR_METHOD.get(compareType).apply(raw).toLowerCase();
        }
    }
}
