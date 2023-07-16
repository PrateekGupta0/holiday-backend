package com.holiday.backend.holiday.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "password")
    private String password;

//    private String roll;

    @Column(name = "role")
    private String role;

    @Column(name = "lastUpdated")
    private Date lastUpdated;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name = "createdOn")
    private Date createOn;

    @Column(name = "userId")
    private String userId;
}
