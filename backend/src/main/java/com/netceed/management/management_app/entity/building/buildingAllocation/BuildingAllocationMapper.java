package com.netceed.management.management_app.entity.building.buildingAllocation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuildingAllocationMapper {

    public BuildingAllocation toEntity(BuildingAllocationDto buildingAllocationDto){
        BuildingAllocation buildingAllocation = new BuildingAllocation(buildingAllocationDto.id(), buildingAllocationDto.building(), buildingAllocationDto.department(), buildingAllocationDto.location());

        if (buildingAllocation.getId() != null){
            buildingAllocation.setId(buildingAllocationDto.id());
        }

        return buildingAllocation;
    }

    public BuildingAllocationDto toDto(BuildingAllocation buildingAllocation){
        if (buildingAllocation == null){
            return null;
        }

        return new BuildingAllocationDto(buildingAllocation.getId(), buildingAllocation.getBuilding(), buildingAllocation.getDepartment(), buildingAllocation.getLocation(), buildingAllocation.getBuildingAllocationDateTime());
    }

    public List<BuildingAllocationDto> convertListBuildingAllocationToDto(List<BuildingAllocation> buildingAllocations){
        List<BuildingAllocationDto> buildingAllocationDtos = new ArrayList<>();
        for (BuildingAllocation buildingAllocation : buildingAllocations){
            BuildingAllocationDto buildingAllocationDto = new BuildingAllocationDto(buildingAllocation.getId(),buildingAllocation.getBuilding(), buildingAllocation.getDepartment(), buildingAllocation.getLocation(), buildingAllocation.getBuildingAllocationDateTime());

            buildingAllocationDtos.add(buildingAllocationDto);
        }
        return buildingAllocationDtos;
    }
}
