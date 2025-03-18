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
    List<Equipment> findAllByStatusEquipment(StatusEquipment statusEquipment);
    @Query("SELECT e FROM Equipment e WHERE e.serialNumber LIKE %:value%")
    List<Equipment> findBKeywordSn(@Param("value") String value);
    @Query("SELECT e FROM Equipment e WHERE e.description LIKE :description%")
    List<Equipment> findEquipmentsStartsWithDescription(@Param("description") String description);
    @Query("SELECT e FROM Equipment e WHERE e.description LIKE %:description%")
    List<Equipment> findEquipmentsContainsDescription(@Param("description") String description);
    @Query("SELECT e FROM Equipment e WHERE e.serialNumber LIKE :serialNumber%")
    List<Equipment> findEquipmentsStartsWithSerialNumber(@Param("serialNumber") String serialNumber);
    @Query("SELECT e FROM Equipment e WHERE e.serialNumber LIKE %:serialNumber%")
    List<Equipment> findEquipmentsContainsSerialNumber(@Param("serialNumber") String serialNumber);
    @Query("SELECT e FROM Equipment e WHERE e.type = :type")
    List<Equipment> findEquipmentsByType(@Param("type") String type);
    @Query("SELECT e FROM Equipment e WHERE e.registryDate LIKE %:registryDate%")
    List<Equipment> findEquipmentsEqualsRegistryDate(@Param("registryDate") String registryDate);
}
