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

        return new EquipmentDto(equipment.getId(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getMacAddress(), equipment.getBrand(),
                equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getWorkstation(),
                equipment.getUnity(), equipment.getRegistryDate(), equipment.getUserEquipments(), equipment.getStatusEquipment(), equipment.getStatusPhysic());
    }

    public EquipmentDto toDtoAssignToUser(Equipment equipment){
        if (equipment == null){
            return null;
        }

        return new EquipmentDto(equipment.getId(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getMacAddress(), equipment.getBrand(),
                equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getWorkstation(),
                equipment.getUnity(), equipment.getRegistryDate(), equipment.getUserEquipments(), equipment.getStatusEquipment(), equipment.getStatusPhysic());
    }

    public EquipmentDto fromDtoToDtoAssignToUser(EquipmentDto equipmentDto){
        if (equipmentDto == null){
            return null;
        }

        return new EquipmentDto(equipmentDto.id(), equipmentDto.description(), equipmentDto.serialNumber(), equipmentDto.macAddress(), equipmentDto.brand(),
                equipmentDto.model(), equipmentDto.type(), equipmentDto.location(), equipmentDto.workstation(), equipmentDto.unity(), equipmentDto.registryDate(), equipmentDto.usersEquipments(),
                equipmentDto.statusEquipment(), equipmentDto.finalCondition());
    }

    public Equipment toEntity(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment(equipmentDto.id(), equipmentDto.description(), equipmentDto.serialNumber(), equipmentDto.macAddress(), equipmentDto.brand(), equipmentDto.model(),
                equipmentDto.type(), equipmentDto.location(), equipmentDto.workstation(), equipmentDto.unity(), equipmentDto.usersEquipments(),
                equipmentDto.statusEquipment(), equipmentDto.finalCondition());

        if (equipmentDto.id() != null) {
            equipment.setId(equipmentDto.id());
        }

        return equipment;
    }

    public List<EquipmentDto> convertListEquipmentToDto(List<Equipment> equipments){
        List<EquipmentDto> equipmentDtos = new ArrayList<>();
        for (Equipment equipment : equipments){
            EquipmentDto equipmentDto = new EquipmentDto(equipment.getId(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getMacAddress(), equipment.getBrand(),
                    equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getWorkstation(), equipment.getUnity(), equipment.getRegistryDate(), equipment.getUserEquipments(),
                    equipment.getStatusEquipment(), equipment.getStatusPhysic());
            equipmentDtos.add(equipmentDto);
        }
        return equipmentDtos;
    }
}
