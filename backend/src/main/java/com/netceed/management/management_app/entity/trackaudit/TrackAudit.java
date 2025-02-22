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
    @Column(name = "entitys_id", nullable = false)
    private List<Long> entitysId;
    @NotBlank
    @Column(nullable = false, length = 100)
    private String action; //CREATE, UPDATE, DELETE
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
    @Column(name = "action_date_time", nullable = false, length = 20)
    private LocalDateTime actionDateTime;
    @NotBlank
    @Column(nullable = false, length = 15)
    private String username;
    @NotBlank
    @Column(nullable = false, length = 15)
    private String entity; //EQUIPMENT, DEPARTMENT, USER...

    public TrackAudit(List<Long> entitysId, String action, LocalDateTime actionDateTime, String entity, String username){
        this.entitysId = entitysId;
        this.action = action;
        this.actionDateTime = actionDateTime;
        this.entity = entity;
        this.username = username;
    }
}
