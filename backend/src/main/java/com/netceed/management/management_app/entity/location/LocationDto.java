package com.netceed.management.management_app.entity.location;

import com.netceed.management.management_app.entity.building.Building;
import com.netceed.management.management_app.entity.department.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationDto(@NotNull Long id, @NotBlank String departmentName, @NotBlank String description, @NotBlank String registryDate, @NotBlank String buildingName, Building building, Department department) { }
