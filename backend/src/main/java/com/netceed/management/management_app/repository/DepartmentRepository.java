package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByValue(String value);

    @Query("select count(p) from User u join u.department p where u.id=:id")
    int getTotalOfEmployees(@Param("id") Long id);
}
