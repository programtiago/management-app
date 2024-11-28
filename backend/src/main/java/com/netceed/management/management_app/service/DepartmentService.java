package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.Department;
import com.netceed.management.management_app.entity.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAllDepartments();
    Department update(DepartmentDto department, Long id);
    boolean valueDepartmentExists(String value);
    DepartmentDto create(Department department);
    DepartmentDto getById(Long id);
    void deleteById(Long id);
    int retrieveTotalOfEmployees(Long departmentId);
}
