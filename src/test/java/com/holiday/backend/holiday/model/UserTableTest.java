package com.holiday.backend.holiday.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class UserTableTest {

    @Test
    public void testUserTable() {
        
        UserTable userTable = new UserTable();

       
        userTable.setId(1);
        userTable.setPassword("password123");
        userTable.setRole("admin");
        userTable.setLastUpdated(new Date());
        userTable.setUpdatedBy("AdminUser");
        userTable.setCreateOn(new Date());
        userTable.setUserId("U12345");

        
        assertEquals(1, userTable.getId());
        assertEquals("password123", userTable.getPassword());
        assertEquals("admin", userTable.getRole());
        assertNotNull(userTable.getLastUpdated());
        assertEquals("AdminUser", userTable.getUpdatedBy());
        assertNotNull(userTable.getCreateOn());
        assertEquals("U12345", userTable.getUserId());
    }
}