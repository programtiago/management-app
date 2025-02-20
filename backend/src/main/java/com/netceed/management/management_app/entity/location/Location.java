package com.netceed.management.management_app.entity.location;

import com.netceed.management.management_app.entity.building.Building;
import com.netceed.management.management_app.entity.department.Department;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_LOCATION")
public class Location {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private Building building;
    private Department department;
    private String registryDate;

}
