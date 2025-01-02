package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByValue(String valueCode);

    //@Query("select count(p) from User u join u.id p where u.department_id=:department_id")
    @Query("SELECT COUNT(id) FROM User u where u.department.id= ?1")
    int getTotalOfEmployeesByDepartment(@Param("id") Long id);
}
