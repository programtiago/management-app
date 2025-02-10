package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByWorkNumber(int workNumber);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN UserDepartment ud ON u.id = ud.user.id WHERE ud.department.id = :departmentId")
    Set<User> findAllByDepartmentId(@Param("departmentId") Long departmentId);
}
