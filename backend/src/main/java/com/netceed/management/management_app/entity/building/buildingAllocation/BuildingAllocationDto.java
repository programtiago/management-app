package com.netceed.management.management_app.entity.building.buildingAllocation;

import com.netceed.management.management_app.entity.building.Building;
import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.location.Location;

public record BuildingAllocationDto(Long id, Building building, Department department, Location location, String registryDate) {
    public static BuildingAllocationDto assignDepartmentsToBuilding(Long id, Building building, Department department){
        return new BuildingAllocationDto(id, building, department, null, null);
    }

    public static BuildingAllocationDto assignLocationsToBuilding(Long id, Building building, Location location){
        return new BuildingAllocationDto(id, building, null, location, null);
    }
}

