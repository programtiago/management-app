package com.netceed.management.management_app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum ShiftType {
    MORNING("MORNING"), AFTERNOON("AFTERNOON"), NIGHT("NIGHT");

    private String code;

    ShiftType(String code){
        this.code = code;
    }

}
