package com.netceed.management.management_app.entity.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationDto(@NotNull Long id, @NotBlank String description, @NotBlank String registryDate, boolean active, boolean available) { }
