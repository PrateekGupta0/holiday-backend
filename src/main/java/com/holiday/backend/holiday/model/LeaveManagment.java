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
    private int id;

    @Column(name="studentId")
    private String studentId;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "lastUpdated")
    private Date lastUpdated;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "createdOn")
    private Date createdOn;
//
//    private String userId;

    @Column(name = "status")
    private String status;

    public LeaveManagment(){};
    public LeaveManagment(String studentId,Date startDate,Date endDate,String reason,Date lastUpdated,String updatedBy,Date create,String status){
        this.studentId=studentId;
        this.startDate=startDate;
        this.endDate=endDate;
        this.reason=reason;
        this.lastUpdated=lastUpdated;
        this.updatedBy=updatedBy;
        this.createdOn=create;
        this.status=status;
    }
}
