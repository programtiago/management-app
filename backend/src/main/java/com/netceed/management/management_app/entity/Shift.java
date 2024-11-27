package com.netceed.management.management_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description; //GENERAL, MORNING, AFTERNOON, NIGHT
    @JsonFormat(pattern = "HH:mm a", shape = JsonFormat.Shape.STRING)
    private LocalTime startTimeShift; //GENERAL: 09:00 AM  MORNING: 07:00 AM  AFTERNOON: 14:00 PM  NIGHT: 00:00 AM OF NEXT DAY
    @JsonFormat(pattern = "HH:mm a", shape = JsonFormat.Shape.STRING)
    private LocalTime endTimeShift; //GENERAL: 18:00 AM  MORNING: 15:30 PM  AFTERNOON: 00:00 AM OF NEXT DAY  NIGHT: 07:00 AM OF NEXT DAY
    private boolean status; //ACTIVE, NOT ACTIVE
    @OneToOne(mappedBy = "shift")
    private User user;

    public Shift(String description, LocalTime startTimeShift, LocalTime endTimeShift, boolean status){
        this.description = description;
        this.startTimeShift = startTimeShift;
        this.endTimeShift = endTimeShift;
    }

}
