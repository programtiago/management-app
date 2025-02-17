package com.netceed.management.management_app.entity.trackaudit;

import jakarta.persistence.*;
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
    private Long entityId; //depending wich type of entity we are working
    private String action; //CREATE, UPDATE, DELETE
    private LocalDateTime actionDateTime;
    private String entity; //EQUIPMENT, DEPARTMENT, USER...
}
