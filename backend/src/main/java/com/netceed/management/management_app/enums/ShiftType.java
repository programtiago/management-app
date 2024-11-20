package com.netceed.management.management_app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShiftType {
    MORNING, AFTERNOON, NIGHT;

    @JsonCreator
    public static ShiftType forName(String name){
        for (ShiftType st : values()){
            if (st.name().equals(name)){
                return st;
            }
        }
        return null;
    }
}
