package com.nhom1.shift_service.kafka;

public enum TopicNames {
    SCHEDULE_DELETED("schedule-deleted"),
    SHIFT_UPDATED("shift-updated"),
    SHIFT_DELETED("shift-deleted");
    private final String name;

    private TopicNames(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
