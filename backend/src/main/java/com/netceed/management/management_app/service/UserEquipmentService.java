package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.UserEquipment;

import java.util.List;

public interface UserEquipmentService {

    List<UserEquipment> getAll();
    UserEquipment assignEquipmentToUser(Long userId, Long equipmentId);
    List<UserEquipment> assignEquipmentsToUser(Long userId, List<Long> equipmentsId);
    void returnEquipmentFromUser(Long userId, Long equipmentId);

}
