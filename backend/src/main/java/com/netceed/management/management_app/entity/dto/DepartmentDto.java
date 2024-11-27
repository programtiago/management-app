package com.netceed.management.management_app.entity.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentDto (Long id, @NotBlank String value, @NotBlank String description, String registryDate,
                             int totalEmployees) { }
