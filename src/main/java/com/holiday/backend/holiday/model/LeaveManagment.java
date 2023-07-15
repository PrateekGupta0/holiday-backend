package com.holiday.backend.holiday.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="LeaveManagment")
public class LeaveManagment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name="studentId")
    private String studentId;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "lastUpdated")
    private String lastUpdated;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "create")
    private String create;
//
//    private String userId;

    @Column(name = "status")
    private boolean status;

    public LeaveManagment(String studentId,Date startDate,Date endDate,String reason,String lastUpdated,String updatedBy,String create){
        this.studentId=studentId;
        this.startDate=startDate;
        this.endDate=endDate;
        this.reason=reason;
        this.lastUpdated=lastUpdated;
        this.updatedBy=updatedBy;
        this.create=create;
        this.status=false;
    }
}
