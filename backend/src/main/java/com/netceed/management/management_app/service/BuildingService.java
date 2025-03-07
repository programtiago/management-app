package com.netceed.management.management_app.service;

import com.netceed.management.management_app.entity.building.Building;
import com.netceed.management.management_app.entity.building.BuildingDto;
import com.netceed.management.management_app.entity.building.BuildingMapper;
import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocation;
import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocationDto;
import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocationMapper;
import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.department.DepartmentMapper;
import com.netceed.management.management_app.exception.ResourceNotFoundException;
import com.netceed.management.management_app.repository.BuildingAllocationRepository;
import com.netceed.management.management_app.repository.BuildingRepository;
import com.netceed.management.management_app.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final DepartmentRepository departmentRepository;
    private final BuildingAllocationRepository buildingAllocationRepository;

    private final BuildingMapper buildingMapper;
    private final BuildingAllocationMapper buildingAllocationMapper;
    private final DepartmentMapper departmentMapper;

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

    public List<BuildingAllocationDto> assignDepartmentsToBuilding(Long buildingId, List<Long> departmentsId){
        Optional<Building> buildingOpt = buildingRepository.findById(buildingId);
        boolean departmentsExists = !departmentRepository.findDepartmentsByDepartmentsId(departmentsId).isEmpty(); //check if departmentsId given exists in db

        List<BuildingAllocationDto> buildingAllocationsDtoList = new ArrayList<>();

        if (buildingOpt.isPresent()){
            Building building = buildingOpt.get();
            if (departmentsExists){
                for (Long departmentId : departmentsId){
                    Optional<Department> departmentOpt = departmentRepository.findById(departmentId);
                    if (departmentOpt.isPresent()){
                       Department department = departmentOpt.get();

                       BuildingAllocation buildingAllocation = new BuildingAllocation();

                       buildingAllocation.setDepartment(department);
                       buildingAllocation.setBuilding(building);
                       buildingAllocation.setLocation(null);
                       buildingAllocation.setLocation(null);
                       buildingAllocation.setBuildingAllocationDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

                       buildingAllocationRepository.save(buildingAllocation);

                       BuildingAllocationDto buildingAllocationDto = new BuildingAllocationDto(buildingAllocation.getId(), buildingAllocation.getBuilding(), buildingAllocation.getDepartment(), buildingAllocation.getLocation(), buildingAllocation.getBuildingAllocationDateTime());
                       //BuildingAllocationDto buildingAllocationDto = BuildingAllocationDto.assignDepartmentsToBuilding(buildingAllocation.getId(), buildingAllocation.getBuilding(), buildingAllocation.getDepartment());
                       buildingAllocationsDtoList.add(buildingAllocationDto);
                    }
                }
            }
        }

        return buildingAllocationsDtoList;


    }
}
