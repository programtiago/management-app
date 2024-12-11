package com.netceed.management.management_app.entity.dto;

import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.enums.StatusEquipment;
import java.util.Set;

public record EquipmentDto(Long id, String description, String serialNumber, String macAddress,
                           String brand, String model, String type, String location, String function,
                           String unity, String registryDate, Set<UserEquipment> usersEquipments,
                           StatusEquipment statusEquipment, String finalCondition) {

    //Create equipment object and assign to a user object
    public static EquipmentDto EquipmentDto(Long id, String description, String serialNumber, String brand, String model, String registryDate, Set<UserEquipment> userEquipments, String type, String unity,
                                            StatusEquipment statusEquipment){
        return new EquipmentDto(id, description, serialNumber, "", brand, model, type, "", "", unity, registryDate, userEquipments, StatusEquipment.IN_USE, "");
    }
}




