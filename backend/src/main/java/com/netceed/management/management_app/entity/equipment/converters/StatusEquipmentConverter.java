package com.netceed.management.management_app.entity.equipment.converters;

import com.netceed.management.management_app.entity.equipment.StatusEquipment;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public class StatusEquipmentConverter implements AttributeConverter<StatusEquipment, String> {
    @Override
    public String convertToDatabaseColumn(StatusEquipment statusEquipment) {
        if (statusEquipment == null){
            return null;
        }

        return statusEquipment.getValue();
    }

    @Override
    public StatusEquipment convertToEntityAttribute(String value) {
        if (value == null){
            return null;
        }

        return Stream.of(StatusEquipment.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
