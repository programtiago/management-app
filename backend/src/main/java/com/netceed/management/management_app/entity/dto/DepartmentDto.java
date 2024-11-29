package com.netceed.management.management_app.entity.dto;

import com.netceed.management.management_app.entity.User;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record DepartmentDto (Long id, @NotBlank String value, @NotBlank String description, String registryDate,
                             int totalEmployees, Set<User> users) { }
