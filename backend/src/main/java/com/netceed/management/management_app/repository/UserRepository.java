package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.user.User;
import com.netceed.management.management_app.entity.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByWorkNumber(int workNumber);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN UserDepartment ud ON u.id = ud.user.id WHERE ud.department.id = :departmentId")
    Set<User> findAllByDepartmentId(@Param("departmentId") Long departmentId);
    @Query("SELECT u FROM User u WHERE u.userAlreadyOnDepartment = false")
    List<User> findByUserAlreadyOnDepartmentTrue();
    @Query("SELECT u FROM User u WHERE u.isActive = true")
    Page<User> findByUsersActive(Pageable pageable);
    @Query("SELECT u FROM User u WHERE u.isActive = false")
    Page<User> findByUsersNotActive(Pageable pageable);
    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:value% OR u.lastName LIKE %:value% OR u.email LIKE %:value%")
    Page<User> findByKeyword(@Param("value") String value, Pageable pageable);
    @Query(value = "SELECT u FROM User u ORDER BY u.id DESC LIMIT 1")
    Optional<User> findLastRecord();
    @Query(value = "SELECT u FROM User u WHERE :userRole = userRole")
    Page<User> findByUserRole(@Param("userRole") UserRole userRole, Pageable pageable);
}
