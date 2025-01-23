package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.building.Building;
import com.netceed.management.management_app.entity.building.BuildingDto;
import com.netceed.management.management_app.entity.building.BuildingMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;

    public List<BuildingDto> getAll(){
        return buildingRepository.findAll().stream().map(buildingMapper::toDto).collect(Collectors.toList());
    }

    public BuildingDto getById(Long id){
        return buildingRepository.findById(id).map(buildingMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Building resource not found with id " + id));
    }

    public BuildingDto create(Building building){
        BuildingDto buildingDto = buildingMapper.toDto(building);

        buildingRepository.save(buildingMapper.toEntity(buildingDto));

        return buildingDto;
    }

}
