package com.netceed.management.management_app.entity.location;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_LOCATION")
public class Location {

    @Id
    @GeneratedValue
    private Long id;
    //private String departmentName;
    @NotBlank
    private String description;
    @NotBlank
    private String registryDate;
    //private String buildingName;
    /*
    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
     */
    public Location(Long id, String description){
        this.id = id;
        this.description = description;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));;
    }

    /*
    public Location(String description, Department department, Building building){
        this.departmentName = department.getDescription();
        this.description = description;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.buildingName = building.getDescription();
    }
     */

}
