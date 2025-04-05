package com.nhom1.clinic_service.kafka;

public enum TopicNames {
    CLINIC_UPDATED("clinic-updated"),
    CLINIC_DELETED("clinic-deleted");
    private final String name;

    private TopicNames(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
