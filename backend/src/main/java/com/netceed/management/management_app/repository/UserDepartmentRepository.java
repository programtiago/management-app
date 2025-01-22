package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.department.userDepartment.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Long> {
}
