package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.equipment.Equipment;
import com.netceed.management.management_app.entity.equipment.StatusEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findBySerialNumber(String serialNumber);
    List<Equipment> findAllByIdIn(List<Long> equipmentsId);
    List<Equipment> findAllByStatusEquipment(StatusEquipment statusEquipment);
    List<Equipment> findByIsActiveTrue();
    @Query("SELECT e FROM Equipment e WHERE e.serialNumber LIKE %:value%")
    List<Equipment> findBKeywordSn(@Param("value") String value);
}
