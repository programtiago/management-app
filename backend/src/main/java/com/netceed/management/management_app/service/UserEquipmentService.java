package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.UserEquipment;

import java.util.List;
import java.util.Optional;

public interface UserEquipmentService {

    List<UserEquipment> getAll();
    void assignUserToEquipment(Long userId, Long equipmentId);
    boolean doesEquipmentBelongToUser(Long equipmentId, Long userId);
    void returnEquipmentFromUser(Long userId, Long equipmentId);

}
