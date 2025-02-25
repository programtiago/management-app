package com.netceed.management.management_app.entity.equipment;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EquipmentMapper {

    public EquipmentDto toDto(Equipment equipment){
        if (equipment == null){
            return null;
        }

        return new EquipmentDto(equipment.getId(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getBrand(),
                equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getWorkstation(),
                equipment.getUnity(), equipment.getRegistryDate(), equipment.getUserEquipments(), equipment.isActive(), convertStatusEquipmentValue(equipment.getStatusEquipment().getValue()), equipment.getStatusPhysic());
    }

    public EquipmentDto toDtoAssignToUser(Equipment equipment){
        if (equipment == null){
            return null;
        }

        return new EquipmentDto(equipment.getId(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getBrand(),
                equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getWorkstation(),
                equipment.getUnity(), equipment.getRegistryDate(), equipment.getUserEquipments(), equipment.isActive(), convertStatusEquipmentValue(equipment.getStatusEquipment().getValue()), equipment.getStatusPhysic());
    }

    public EquipmentDto fromDtoToDtoAssignToUser(EquipmentDto equipmentDto){
        if (equipmentDto == null){
            return null;
        }

        return new EquipmentDto(equipmentDto.id(), equipmentDto.description(), equipmentDto.serialNumber(), equipmentDto.brand(),
                equipmentDto.model(), equipmentDto.type(), equipmentDto.location(), equipmentDto.workstation(), equipmentDto.unity(), equipmentDto.registryDate(), equipmentDto.usersEquipments(),
                equipmentDto.isActive(), convertStatusEquipmentValue(equipmentDto.statusEquipment().getValue()), equipmentDto.finalCondition());
    }

    public Equipment toEntity(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment(equipmentDto.id(), equipmentDto.description(), equipmentDto.serialNumber(), equipmentDto.brand(), equipmentDto.model(), equipmentDto.type(),
                equipmentDto.location(), equipmentDto.workstation(), equipmentDto.unity(), equipmentDto.isActive(), convertStatusEquipmentValue(StatusEquipment.AVAILABLE.getValue()), equipmentDto.finalCondition());

        if (equipmentDto.id() != null) {
            equipment.setId(equipmentDto.id());
        }

        return equipment;
    }

    public List<EquipmentDto> convertListEquipmentToDto(List<Equipment> equipments){
        List<EquipmentDto> equipmentDtos = new ArrayList<>();
        for (Equipment equipment : equipments){
            EquipmentDto equipmentDto = new EquipmentDto(equipment.getId(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getBrand(),
                    equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getWorkstation(), equipment.getUnity(), equipment.getRegistryDate(), equipment.getUserEquipments(), equipment.isActive(),
                    convertStatusEquipmentValue(equipment.getStatusEquipment().getValue()), equipment.getStatusPhysic());
            equipmentDtos.add(equipmentDto);
        }
        return equipmentDtos;
    }

    public StatusEquipment convertStatusEquipmentValue(String value){
        if (value == null){
            return null;
        }

        return switch (value){
            case "Available" -> StatusEquipment.AVAILABLE;
            case "Not Available" -> StatusEquipment.NOT_AVAILABLE;
            case "On Warranty" -> StatusEquipment.ON_WARRANTY;
            case "In Use" -> StatusEquipment.IN_USE;
            case "For Warranty" -> StatusEquipment.FOR_WARRANTY;
            default -> throw new IllegalArgumentException("Status Equipment invalid: " + value);
        };
    }
}
