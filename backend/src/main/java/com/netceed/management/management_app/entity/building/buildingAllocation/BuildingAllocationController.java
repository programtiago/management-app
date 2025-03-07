package com.netceed.management.management_app.entity.building.buildingAllocation;

import com.netceed.management.management_app.service.BuildingAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/building-allocations")
@RequiredArgsConstructor
public class BuildingAllocationController {

    private final BuildingAllocationService buildingAllocationService;

    @GetMapping
    public List<BuildingAllocationDto> getAll(){
        return buildingAllocationService.getAllAssignments();
    }
}
