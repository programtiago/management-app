package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;


import java.util.List;

public interface EquipmentService {

    void delete(Long id);
    EquipmentDto create(Equipment equipment);
    EquipmentDto createEquipmentForUser(Equipment equipment, Long userId);
    List<EquipmentDto> getAllEquipments();
    boolean serialNumberExists(String serialNumber);
    Equipment update(EquipmentDto equipment, Long id);
    EquipmentDto getById(Long id);
    List<EquipmentDto> findEquipmentsByIds(List<Long> equipemntsId);
    List<EquipmentDto> getEquipmentsAvailable();

}
