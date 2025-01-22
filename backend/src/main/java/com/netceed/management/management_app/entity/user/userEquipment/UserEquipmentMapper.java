package com.netceed.management.management_app.entity.user.userEquipment;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserEquipmentMapper {

    public UserEquipmentDto toDto(UserEquipment userEquipment){
        if (userEquipment == null){
            return null;
        }

        return new UserEquipmentDto(userEquipment.getId(), userEquipment.getUser(), userEquipment.getEquipment(), userEquipment.getAssignedDateTime(), userEquipment.getReturnDateTime(),
                userEquipment.getComments());
    }

    public UserEquipment toEntity(UserEquipmentDto userEquipmentDto){
        UserEquipment userEquipment = new UserEquipment(userEquipmentDto.id(), userEquipmentDto.user(), userEquipmentDto.equipment(), userEquipmentDto.assignedDate(),
                userEquipmentDto.returnDate(), userEquipmentDto.comments());

        if (userEquipmentDto.id() != null) {
            userEquipment.setId(userEquipmentDto.id());
        }
        return userEquipment;
    }

    public List<UserEquipmentDto> convertListUserEquipmentsToDto(List<UserEquipment> userEquipments){
        List<UserEquipmentDto> userEquipmentsDtos = new ArrayList<>();
        for (UserEquipment userEquipment : userEquipments){
            UserEquipmentDto userEquipmentDto = new UserEquipmentDto(userEquipment.getId(), userEquipment.getUser(), userEquipment.getEquipment(), userEquipment.getAssignedDateTime(), userEquipment.getReturnDateTime(), userEquipment.getComments());
            userEquipmentsDtos.add(userEquipmentDto);
        }
        return userEquipmentsDtos;
    }
}
