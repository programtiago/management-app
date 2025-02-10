package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.user.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByCodeValue(String valueCode);

    //@Query("select count(p) from User u join u.id p where u.department_id=:department_id")
    @Query("SELECT totalEmployees FROM Department d where d.id = ?1")
    int getTotalOfEmployeesByDepartment(@Param("id") Long id);
}
