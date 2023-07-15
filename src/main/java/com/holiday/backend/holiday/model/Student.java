package com.holiday.backend.holiday.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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
    private Timestamp lastUpdated;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "create")
    private Timestamp create;

    @Column(name = "userId")
    private String userId;


}
