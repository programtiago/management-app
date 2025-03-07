package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocationDto;
import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocationMapper;
import com.netceed.management.management_app.repository.BuildingAllocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingAllocationService {

    private final BuildingAllocationRepository buildingAllocationRepository;
    private final BuildingAllocationMapper buildingAllocationMapper;

    public List<BuildingAllocationDto> getAllAssignments(){
        return buildingAllocationMapper.convertListBuildingAllocationToDto(buildingAllocationRepository.findAll());
    }
}
