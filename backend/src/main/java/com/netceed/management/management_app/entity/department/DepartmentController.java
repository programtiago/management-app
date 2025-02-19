package com.netceed.management.management_app.entity.department;

import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final TrackAuditService trackAuditService;

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

    @PutMapping("/{id}")
    public void update(@RequestBody @Valid DepartmentDto department, @PathVariable Long id){
        departmentService.update(department, id);
        trackAuditService.logAction(Collections.singletonList(department.id()), "Updated data of department with code " + department.value(), "testUsername", "Department");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        DepartmentDto departmentDto = departmentService.getById(id);

        departmentService.deleteById(id);
        trackAuditService.logAction(Collections.singletonList(departmentDto.id()), "Deleted department with code " + departmentDto.value(), "testUsername", "Department");
    }

    @GetMapping("/totalEmployees/{id}")
    public int getTotalEmployeesForDepartment(@PathVariable Long id){
        return departmentService.retrieveTotalOfEmployees(id);
    }
}
