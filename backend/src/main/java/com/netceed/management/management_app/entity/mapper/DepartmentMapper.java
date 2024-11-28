package com.netceed.management.management_app.entity.mapper;

import com.netceed.management.management_app.entity.Department;
import com.netceed.management.management_app.entity.dto.DepartmentDto;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(Department department){
        if (department == null){
            return null;
        }

        return new DepartmentDto(department.getId(), department.getValue(), department.getDescription(), department.getRegistryDate(), department.getTotalEmployees(), department.getUsers());
    }

    public Department toEntity(DepartmentDto departmentDto){
        Department department = new Department(departmentDto.value(), departmentDto.description(), departmentDto.totalEmployees(), departmentDto.users());

        if (departmentDto.id() != null)
        {
            department.setId(departmentDto.id());
        }
        return department;
    }
}
