package com.netceed.management.management_app.entity.department;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(Department department){
        if (department == null){
            return null;
        }

        return new DepartmentDto(department.getId(), department.getCodeValue(), department.getDescription(), department.getRegistryDate(), department.getTotalEmployees());
    }

    public Department toEntity(DepartmentDto departmentDto){
        Department department = new Department(departmentDto.id(), departmentDto.value(), departmentDto.description(), departmentDto.totalEmployees());

        if (departmentDto.id() != null)
        {
            department.setId(departmentDto.id());
        }
        return department;
    }

    public List<DepartmentDto> convertListBuildingAllocationToDto(List<Department> departments){
        List<DepartmentDto> departmentsDtos = new ArrayList<>();
        for (Department department : departments){
            DepartmentDto departmentDto = new DepartmentDto(department.getId(), department.getCodeValue(), department.getDescription(), department.getRegistryDate(), department.getTotalEmployees());

            departmentsDtos.add(departmentDto);
        }
        return departmentsDtos;
    }
}
