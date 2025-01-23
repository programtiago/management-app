package com.netceed.management.management_app.entity.building;

import org.springframework.stereotype.Component;

@Component
public class BuildingMapper {

    public BuildingDto toDto(Building building){
        if (building == null){
            return null;
        }

        return new BuildingDto(building.getId(), building.getDescription(), building.isActive(), building.getRegistryDate());
    }

    public Building toEntity(BuildingDto buildingDto){
        Building building = new Building(buildingDto.id(), buildingDto.description(), buildingDto.isActive(), buildingDto.registryDate());

        if (buildingDto.id() != null) {
            building.setId(buildingDto.id());
        }

        return building;
    }
}
