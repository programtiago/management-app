package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.UserEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEquipmentRepository extends JpaRepository<UserEquipment, Long> {
    Optional<UserEquipment> findUserEquipmentByEquipmentId(Long equipmentId);
}
