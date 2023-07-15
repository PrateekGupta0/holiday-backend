package com.holiday.backend.holiday.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Warden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "permanentAddress")
    private String permanentAddress;

    @Column(name = "lastUpdated")
    private Timestamp lastUpdated;

//    private String updatedBy;
    @Column(name = "create")
    private Timestamp create;

//    private String userId;
}
