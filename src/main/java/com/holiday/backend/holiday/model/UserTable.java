package com.holiday.backend.holiday.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

//    private String roll;

    @Column(name = "role")
    private String role;

    @Column(name = "lastUpdated")
    private String lastUpdated;

    @Column(name="updatedBy")
    private String updatedBy;

    @Column(name = "createdOn")
    private String createOn;

    @Column(name = "userId")
    private String userId;
}
