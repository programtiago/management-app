package com.netceed.management.management_app.entity.mapper;


import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {

    public EquipmentDto toDto(Equipment equipment){
        if (equipment == null){
            return null;
        }

        return new EquipmentDto(equipment.getId(), equipment.getDescription(), equipment.getSerialNumber(), equipment.getMacAddress(), equipment.getBrand(),
                equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getGoal(),
                equipment.getUnity(), equipment.getRegistryDate(), equipment.getUsers(), equipment.getAllocationDateTime(), equipment.getReturningDateTime(), equipment.getStatusEquipment(), equipment.getStatusPhysic());
    }

    public Equipment toEntity(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment(equipmentDto.id(), equipmentDto.description(), equipmentDto.serialNumber(), equipmentDto.macAddress(), equipmentDto.brand(), equipmentDto.model(),
                equipmentDto.type(), equipmentDto.location(), equipmentDto.function(), equipmentDto.unity(), equipmentDto.registryDate(), equipmentDto.users(), equipmentDto.allocationDateTime(),
                equipmentDto.returningDateTime(), equipmentDto.statusEquipment(), equipmentDto.finalCondition());

        if (equipmentDto.id() != null) {
            equipment.setId(equipmentDto.id());
        }
        return equipment;
    }
}
