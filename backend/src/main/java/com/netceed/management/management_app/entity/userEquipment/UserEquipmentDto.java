package com.netceed.management.management_app.entity.userEquipment;

import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.user.User;

import java.time.LocalDateTime;

public record UserEquipmentDto (Long id, User user, Equipment equipment, LocalDateTime assignedDate, LocalDateTime returnDate, String comments){
}
