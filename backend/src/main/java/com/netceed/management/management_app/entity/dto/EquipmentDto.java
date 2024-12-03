package com.netceed.management.management_app.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netceed.management.management_app.entity.User;
import com.netceed.management.management_app.entity.UserEquipment;
import com.netceed.management.management_app.enums.StatusEquipment;

import java.util.List;
import java.util.Set;

public record EquipmentDto(Long id, String description, String serialNumber, String macAddress,
                           String brand, String model, String type, String location, String function,
                           String unity, String registryDate, Set<UserEquipment> usersEquipments,
                           StatusEquipment statusEquipment, String finalCondition) { }




