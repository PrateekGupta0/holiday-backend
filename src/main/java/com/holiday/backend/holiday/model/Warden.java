package com.holiday.backend.holiday.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
public class Warden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "permanentAddress")
    private String permanentAddress;

    @Column(name = "lastUpdated")
    private Date lastUpdated;

//    private String updatedBy;
    @Column(name = "createdOn")
    private Date createdOn;

//    private String userId;
}
