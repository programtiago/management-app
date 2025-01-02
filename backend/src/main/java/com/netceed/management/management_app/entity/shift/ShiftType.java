package com.netceed.management.management_app.entity.shift;

import lombok.Getter;

@Getter
public enum ShiftType {
    MORNING("MORNING"), AFTERNOON("AFTERNOON"), NIGHT("NIGHT");

    private String code;

    ShiftType(String code){
        this.code = code;
    }

}
