package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.department.DepartmentDto;
import com.netceed.management.management_app.entity.department.DepartmentMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public List<DepartmentDto> getAllDepartments() {
       return departmentRepository.findAll()
               .stream().map(departmentMapper::toDto)
               .collect(Collectors.toList());
    }

    public DepartmentDto getById(Long id) {
        return departmentRepository.findById(id).map(departmentMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Department resource not found with id " + id));
    }

    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    public int retrieveTotalOfEmployees(Long departmentId) {
        return departmentRepository.getTotalOfEmployeesByDepartment(departmentId);
    }

    public Department update(DepartmentDto departmentUpdate, Long id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setDescription(departmentUpdate.description());
                    department.setCodeValue(departmentUpdate.value());
                    department.setTotalEmployees(departmentUpdate.totalEmployees());

                    return departmentRepository.save(department);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    public boolean valueDepartmentExists(String value) {
        return departmentRepository.findByCodeValue(value).isPresent();
    }

    public DepartmentDto create(Department department) {
        departmentRepository.save(department);

        return departmentMapper.toDto(department);
    }
}
