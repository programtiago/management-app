package com.netceed.management.management_app.entity.userEquipment;

import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.user.User;

import java.time.LocalDateTime;

public record UserEquipmentDto (Long id, User user, Equipment equipment, String assignedDate, String returnDate, String comments) {
    public UserEquipmentDto(Long id, User user, Equipment equipment, String assignedDate, String returnDate, String comments) {
        this.id = id;
        this.user = user;
        this.equipment = equipment;
        this.assignedDate = assignedDate;
        this.returnDate = returnDate;
        this.comments = comments;
    }
    public UserEquipmentDto setId(Long id){
        return new UserEquipmentDto(id, user, equipment, assignedDate, returnDate, comments);
    }

    public UserEquipmentDto setUser(User user){
        return new UserEquipmentDto(id, user, equipment, assignedDate, returnDate, comments);
    }

    public UserEquipmentDto setEquipment(Equipment equipment){
        return new UserEquipmentDto(id, user, equipment, assignedDate, returnDate, comments);
    }

    public UserEquipmentDto setAssignedDate(String assignedDate){
        return new UserEquipmentDto(id, user, equipment, assignedDate, returnDate, comments);
    }

    public UserEquipmentDto setReturnDate(String returnDate){
        return new UserEquipmentDto(id, user, equipment, assignedDate, returnDate, comments);
    }
    public UserEquipmentDto setComments(String comments){
        return new UserEquipmentDto(id, user, equipment, assignedDate, returnDate, comments);
    }
}

