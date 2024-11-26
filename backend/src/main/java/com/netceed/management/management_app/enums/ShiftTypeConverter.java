package com.netceed.management.management_app.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ShiftTypeConverter implements AttributeConverter<ShiftType, String> {

    @Override
    public String convertToDatabaseColumn(ShiftType attribute) {
        if (attribute == null){
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ShiftType convertToEntityAttribute(String dbData) {
        if (dbData == null){
            return null;
        }
        return Stream.of(ShiftType.values())
                .filter(s -> s.getCode().equalsIgnoreCase(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
