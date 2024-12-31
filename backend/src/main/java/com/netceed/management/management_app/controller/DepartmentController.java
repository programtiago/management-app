package com.netceed.management.management_app.controller;

import com.netceed.management.management_app.entity.dto.DepartmentDto;
import com.netceed.management.management_app.service.impl.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> getAllDepartments() throws Exception {
        List<DepartmentDto> departmentsFound = departmentService.getAllDepartments();

        if (departmentsFound.isEmpty())
            throw new Exception("No departments to retrieve.");
        return departmentsFound;
    }

    @GetMapping("/{id}")
    public DepartmentDto getById(@PathVariable Long id){
        return departmentService.getById(id);
    }

    /*
    @PostMapping("/new")
    public DepartmentDto create(@RequestBody @Valid DepartmentDto newDepartment){
        //Each department code has to be different - Department code associate only one department ?? maybe in future this changes
        boolean codeDepartmentAlreadyExists = departmentService.valueDepartmentExists(newDepartment.value());

        if (codeDepartmentAlreadyExists){
            throw new IllegalArgumentException("Department Value " + newDepartment.value() + " already exists.");
        }

        return departmentService.create(newDepartment);
    }

     */

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid DepartmentDto department, @PathVariable Long id){
        departmentService.update(department, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        departmentService.deleteById(id);
    }

    @GetMapping("/totalEmployees/{id}")
    public int getTotalEmployeesForDepartment(@PathVariable Long id){
        return departmentService.retrieveTotalOfEmployees(id);
    }
}
