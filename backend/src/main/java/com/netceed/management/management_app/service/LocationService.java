package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.building.BuildingDto;
import com.netceed.management.management_app.entity.building.BuildingMapper;
import com.netceed.management.management_app.entity.department.DepartmentDto;
import com.netceed.management.management_app.entity.department.DepartmentMapper;
import com.netceed.management.management_app.entity.location.Location;
import com.netceed.management.management_app.entity.location.LocationDto;
import com.netceed.management.management_app.entity.location.LocationMapper;
import com.netceed.management.management_app.repository.BuildingRepository;
import com.netceed.management.management_app.repository.DepartmentRepository;
import com.netceed.management.management_app.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final DepartmentRepository departmentRepository;
    private final BuildingRepository buildingRepository;

    private final BuildingMapper buildingMapper;
    private final DepartmentMapper departmentMapper;
    private final LocationMapper locationMapper;

    public List<LocationDto> getAll(){
        return locationMapper.convertListLocationToDto(locationRepository.findAll());
    }

    public LocationDto create(Location location){
        return locationMapper.toDto(locationRepository.save(location));
    }

    /*
    public LocationDto createLocation(Long departmentId, Long buildingId, String description){
        DepartmentDto departmentDto = departmentMapper.toDto(departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("No department found with department id " + departmentId)));
        BuildingDto buildingDto = buildingMapper.toDto(buildingRepository.findById(buildingId).orElseThrow(() -> new RuntimeException("No department found with department id " + departmentId)));

        Location location = new Location();

        location.setDescription(description);
        location.setDepartmentName(departmentDto.description());
        location.setBuildingName(buildingDto.description());
        location.setBuilding(buildingMapper.toEntity(buildingDto));
        location.setDepartment(departmentMapper.toEntity(departmentDto));


        return locationMapper.toDto(location);
    }

     */
}
