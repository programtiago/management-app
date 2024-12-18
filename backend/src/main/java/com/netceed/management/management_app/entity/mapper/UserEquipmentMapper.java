package com.netceed.management.management_app.entity.mapper;

import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.UserEquipmentDto;
import org.springframework.stereotype.Component;

@Component
public class UserEquipmentMapper {

    public UserEquipmentDto toDto(UserEquipment userEquipment){
        if (userEquipment == null){
            return null;
        }

        return new UserEquipmentDto(userEquipment.getId(), userEquipment.getUser(), userEquipment.getEquipment(), userEquipment.getAssignedDate(), userEquipment.getReturnDate(),
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
}
