package com.holiday.backend.holiday.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "batch")
    private String batch;

    @Column(name = "division")
    private String division;

    @Column(name = "rollNo")
    private String rollNo;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "permanentAddress")
    private String permanentAddress;

    @Column(name = "lastUpdated")
    private Date lastUpdated;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "createdOn")
    private Date createOn;

    @Column(name = "studentId")
    private String studentId;


}
