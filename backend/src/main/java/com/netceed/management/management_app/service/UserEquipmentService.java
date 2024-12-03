package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.entity.dto.UserEquipmentDto;

import java.util.List;

public interface UserEquipmentService {

    List<UserEquipment> getAll();
    UserEquipment assignUserToEquipment(Long userId, Long equipmentId);
    void returnEquipmentFromUser(Long userId, Long equipmentId);

}
