package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.user.userDepartment.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDepartmentRepository extends JpaRepository<UserDepartment, Long> {

    //Given departmentId and UserId returns the respective assignment/s
    @Query("SELECT ue FROM UserDepartment ue WHERE ue.department.id = :departmentId AND ue.user.id = :userId")
    UserDepartment findUserDepartmentByUserIdAndDepartmentId(@Param("departmentId") Long departmentId, @Param("userId") Long userId);

}
