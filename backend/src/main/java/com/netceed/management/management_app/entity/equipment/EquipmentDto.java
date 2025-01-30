package com.netceed.management.management_app.entity.equipment;

import com.netceed.management.management_app.entity.user.userEquipment.UserEquipment;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record EquipmentDto(Long id, @NotNull String description, @NotNull String serialNumber,
                           @NotNull String brand, @NotNull String model, String type, String location, String workstation,
                           @NotNull String unity, @NotNull String registryDate, Set<UserEquipment> usersEquipments, @NotNull boolean isActive,
                           @NotNull StatusEquipment statusEquipment, String finalCondition) {


    public EquipmentDto setId(Long id) {
        return new EquipmentDto(id, description, serialNumber, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setIsActive(boolean active) {
        return new EquipmentDto(id, description, serialNumber, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, active, statusEquipment, finalCondition);
    }

    public EquipmentDto setRegistryDate(String registryDateValue) {
        return new EquipmentDto(id, description, serialNumber, brand, model, type, location, workstation,
                unity, registryDateValue, usersEquipments, isActive, statusEquipment, finalCondition);
    }
}




