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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "description", length = 50, nullable = false)
    private String description;
    @NotBlank
    @Column(name = "registry_date", nullable = false)
    private String registryDate;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "is_available")
    private boolean available;
    public Location(Long id, String description, boolean active, boolean available){
        this.id = id;
        this.description = description;
        this.registryDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.active = active;
        this.available = available;
    }
}
