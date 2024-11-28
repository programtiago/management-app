package com.netceed.management.management_app.service.impl;

import com.netceed.management.management_app.entity.Department;
import com.netceed.management.management_app.entity.dto.DepartmentDto;
import com.netceed.management.management_app.entity.mapper.DepartmentMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> getAllDepartments() {
       return departmentRepository.findAll()
               .stream().map(departmentMapper::toDto)
               .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getById(Long id) {
        return departmentRepository.findById(id).map(departmentMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Department resource not found with id " + id));
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public int retrieveTotalOfEmployees(Long departmentId) {
        return departmentRepository.getTotalOfEmployees(departmentId);
    }


    @Override
    public Department update(DepartmentDto departmentUpdate, Long id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setDescription(departmentUpdate.description());
                    department.setValue(departmentUpdate.value());
                    department.setTotalEmployees(departmentUpdate.totalEmployees());

                    return departmentRepository.save(department);
                }).orElseThrow(() -> new ResourceNotFoundException("Operation failed because the resource with the id " + id + " doesn't exist."));
    }

    @Override
    public boolean valueDepartmentExists(String value) {
        return departmentRepository.findByValue(value).isPresent();
    }

    @Override
    public DepartmentDto create(Department department) {
        departmentRepository.save(department);

        return departmentMapper.toDto(department);
    }

}
