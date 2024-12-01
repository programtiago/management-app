package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.Equipment;
import com.netceed.management.management_app.entity.dto.EquipmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findBySerialNumber(String serialNumber);
    Optional<Equipment> findByIdAndUsersIsNull(Long id);
}
