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

    public EquipmentDto setDescription(String description) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setSerialNumber(String serialNumber) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setMacAddress(String mac) {
        return new EquipmentDto(id, description, serialNumber, mac, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setBrand(String brand) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setModel(String model) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setLocation(String location) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setType(String type) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setWorkstation(String workstation) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setUnity(String unity) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setRegistryDate(String registryDate) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setUsersEquipments(Set<UserEquipment> usersEquipments) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setStatusEquipment(StatusEquipment statusEquipment) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }

    public EquipmentDto setIsActive(boolean active) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, active, statusEquipment, finalCondition);
    }

    public EquipmentDto setFinalCondition(String finalCondition) {
        return new EquipmentDto(id, description, serialNumber, macAddress, brand, model, type, location, workstation,
                unity, registryDate, usersEquipments, isActive, statusEquipment, finalCondition);
    }
}




