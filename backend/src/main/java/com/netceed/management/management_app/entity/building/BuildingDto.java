package com.netceed.management.management_app.entity.building;

import jakarta.validation.constraints.NotNull;

public record BuildingDto (Long id, @NotNull String description, @NotNull boolean isActive, @NotNull String registryDate) {

}
