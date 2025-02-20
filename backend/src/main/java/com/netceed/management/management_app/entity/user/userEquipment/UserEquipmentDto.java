package com.netceed.management.management_app.entity.user.userEquipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.user.User;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record UserEquipmentDto (Long id, User user, Equipment equipment, @Column(nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss") LocalDateTime assignedDate, LocalDateTime returnDate, String comments) {
    public UserEquipmentDto(Long id, User user, Equipment equipment, LocalDateTime assignedDate, LocalDateTime returnDate, String comments) {
        this.id = id;
        this.user = user;
        this.equipment = equipment;
        this.assignedDate = assignedDate;
        this.returnDate = returnDate;
        this.comments = comments;
    }
}

