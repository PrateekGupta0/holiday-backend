package com.holiday.backend.holiday.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class WardenTest {

    @Test
    public void testWarden() {
        
        Warden warden = new Warden();

        
        warden.setId(1);
        warden.setFullName("John Doe");
        warden.setMobileNo("1234567890");
        warden.setPermanentAddress("123 Main St");
        warden.setLastUpdated(new Date());
        warden.setCreatedOn(new Date());

    
        assertEquals(1, warden.getId());
        assertEquals("John Doe", warden.getFullName());
        assertEquals("1234567890", warden.getMobileNo());
        assertEquals("123 Main St", warden.getPermanentAddress());
        assertNotNull(warden.getLastUpdated());
        assertNotNull(warden.getCreatedOn());
    }
}