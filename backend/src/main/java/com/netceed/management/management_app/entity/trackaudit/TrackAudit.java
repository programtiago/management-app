package com.netceed.management.management_app.entity.trackaudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** This class is responsible to store each interaction made in a certain entity
 *              EX: USER WAS CREATED AT 2024/05/12T22:32:12 **/
@Table(name = "TB_HISTORY")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String action; //CREATE, UPDATE, DELETE
    @NotNull
    private LocalDateTime actionDateTime;
    @NotBlank
    private String entity; //EQUIPMENT, DEPARTMENT, USER...

    public TrackAudit(String action, LocalDateTime actionDateTime, String entity){
        this.action = action;
        this.actionDateTime = actionDateTime;
        this.entity = entity;
    }
}
