package com.netceed.management.management_app.entity.building;

import com.netceed.management.management_app.entity.building.buildingAllocation.BuildingAllocationDto;
import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.trackaudit.TrackAuditService;
import com.netceed.management.management_app.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/buildings")
@RestController
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;
    private final TrackAuditService trackAuditService;

    @GetMapping
    public List<BuildingDto> getAll(){
        return buildingService.getAll();
    }

    @PostMapping("/{buildingId}/departments")
    public List<BuildingAllocationDto> assignDepartmentsToBuilding(@PathVariable Long buildingId, @RequestBody List<Long> departmentsId){
        BuildingDto building = buildingService.getById(buildingId);
        List<BuildingAllocationDto> buildingAssignmentsList = buildingService.assignDepartmentsToBuilding(buildingId, departmentsId);

        for (BuildingAllocationDto buildingAllocationDto : buildingAssignmentsList){
            if (!buildingAssignmentsList.isEmpty() && buildingAllocationDto != null){
                trackAuditService.logAction(buildingAllocationDto.id(), "Assigned department with the code  " + buildingAllocationDto.department().getCodeValue() + " to building [ " + building.description() + " ] at " +
                        buildingAllocationDto.registryDate(), "testusername", "BuildingAllocation");

            }
        }
        return buildingService.assignDepartmentsToBuilding(buildingId, departmentsId);
    }
}
