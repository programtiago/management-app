package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByWorkNumber(int workNumber);
    Optional<User> findByEmail(String email);
}
