package com.netceed.management.management_app.entity.equipment;

import com.netceed.management.management_app.entity.userEquipment.UserEquipment;

import java.util.Set;

public record EquipmentDto(Long id, String description, String serialNumber, String macAddress,
                           String brand, String model, String type, String location, String workstation,
                           String unity, String registryDate, Set<UserEquipment> usersEquipments, boolean isActive,
                           StatusEquipment statusEquipment, String finalCondition) {

    //Create equipment object and assign to a user object
    public EquipmentDto setId(Long id) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setIsActive(boolean active) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, active, statusEquipment, finalCondition);
    }

    public static EquipmentDto createNewEquipmentAndAssignToEntity(Long id, String description, String serialNumber, String macAddress, String brand, String model, String location, String type, String unity, String registryDate, String workstation, Set<UserEquipment> userEquipments,
                                                                   boolean isActive, StatusEquipment statusEquipment, String finalCondition){
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, location, type, unity, registryDate, workstation, userEquipments, isActive, statusEquipment, finalCondition);
    }
}




