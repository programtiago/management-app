package com.netceed.management.management_app.entity.building;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_BUILDING")
/**  Physic space that represents the building - A corporation can have multiple buildings -> Building A, Building B.
Each Building can have multiple distinct locations. Building A -> Location AY, Location AX    Building B -> Location AS, Location AJ
Each Building has one department at least
Each Department can have the same Location
 **/
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "description", nullable = false, length = 50)
    private String description; //BUILDING A : ADMINISTRATIVE BUILDING   BUILDING B: FACTORY BUILDING
    @NotNull
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    @NotNull
    @Column(name = "registry_date", nullable = false, length = 19)
    private String registryDate;
}
