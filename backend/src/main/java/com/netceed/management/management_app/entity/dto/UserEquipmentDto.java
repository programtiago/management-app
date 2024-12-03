package com.netceed.management.management_app.entity.dto;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.User;

import java.time.LocalDateTime;

public record UserEquipmentDto (Long id, User user, Equipment equipment, LocalDateTime assignedDate, LocalDateTime returnDate, String comments){
}
