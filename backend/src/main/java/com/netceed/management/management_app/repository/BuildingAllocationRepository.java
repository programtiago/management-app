package com.netceed.management.management_app.repository;

import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingAllocationRepository extends JpaRepository<BuildingAllocation, Long> {
}
