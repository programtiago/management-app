package com.netceed.management.management_app.entity.equipment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEquipment {
    AVAILABLE("Available"), NOT_AVAILABLE("Not Available"), ON_WARRANTY("On Warranty"), FOR_WARRANTY("For Warranty"), IN_USE("In Use");
    private String value;

    private StatusEquipment(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }
}
