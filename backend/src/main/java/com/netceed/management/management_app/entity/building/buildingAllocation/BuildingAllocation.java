package com.netceed.management.management_app.entity.building.buildingAllocation;

import com.netceed.management.management_app.entity.building.Building;
import com.netceed.management.management_app.entity.department.Department;
import com.netceed.management.management_app.entity.location.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "TB_BUILDING_ALLOCATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @NotBlank
    @Column(name = "building_allocation_date_time", nullable = false, updatable = false)
    private String buildingAllocationDateTime;

    public BuildingAllocation(Long id, Building building, Department department, Location location){
        this.id = id;
        this.building = building;
        this.department = department;
        this.location = location;
        this.buildingAllocationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    public BuildingAllocation(Building building, Department department, Location location){
        this.building = building;
        this.department = department;
        this.location = location;
        this.buildingAllocationDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
