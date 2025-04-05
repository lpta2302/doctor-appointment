package com.nhom1.doctor_service.kafka;

public enum TopicNames {
    SPECIALIZATION_UPDATED("specialization-updated"),
    SPECIALIZATION_DELETED("specialization-deleted"),
    DOCTOR_UPDATED("doctor-updated"),
    DOCTOR_DELETED("doctor-deleted");
    private final String name;

    private TopicNames(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
