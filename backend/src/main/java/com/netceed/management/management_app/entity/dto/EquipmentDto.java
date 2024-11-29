package com.netceed.management.management_app.entity.dto;

import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.enums.StatusEquipment;

import java.time.LocalDateTime;

public record EquipmentDto(Long id, String description, String serialNumber, String macAddress,
                           String brand, String model, String type, String location, String function,
                           String unity, String registryDate, User user, LocalDateTime allocationDateTime, LocalDateTime returningDateTime,
                           StatusEquipment statusEquipment, String finalCondition) { }




