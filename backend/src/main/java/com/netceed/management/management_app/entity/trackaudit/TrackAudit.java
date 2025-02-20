package com.netceed.management.management_app.entity.trackaudit;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    @NotNull
    private List<Long> entitysId;
    @NotBlank
    private String action; //CREATE, UPDATE, DELETE
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime actionDateTime;
    @NotBlank
    private String username;
    @NotBlank
    private String entity; //EQUIPMENT, DEPARTMENT, USER...

    public TrackAudit(List<Long> entitysId, String action, LocalDateTime actionDateTime, String entity, String username){
        this.entitysId = entitysId;
        this.action = action;
        this.actionDateTime = actionDateTime;
        this.entity = entity;
        this.username = username;
    }
}
