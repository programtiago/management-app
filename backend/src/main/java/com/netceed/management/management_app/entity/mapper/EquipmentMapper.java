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
                equipment.getModel(), equipment.getType(), equipment.getLocation(), equipment.getFunction(),
                equipment.getUnity(), equipment.getUser(), equipment.getAllocationDateTime(), equipment.getReturningDateTime(), equipment.getStatusEquipment(), equipment.getFinalCondition());
    }

    public Equipment toEntity(EquipmentDto equipmentDto) {
        Equipment equipment = new Equipment(equipmentDto.description(), equipmentDto.serialNumber(), equipmentDto.macAddress(), equipmentDto.);

        if (equipmentDto.id() != null) {
            equipment.setId(equipmentDto.id());
        }
        return equipment;
    }
}
