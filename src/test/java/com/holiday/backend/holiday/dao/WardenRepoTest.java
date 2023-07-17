package com.holiday.backend.holiday.dao;
import com.holiday.backend.holiday.model.Warden;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WardenRepoTest {

    @Autowired
    private WardenRepo wardenRepo;

    @Test
    public void testSaveWarden() {
        
        Warden warden = new Warden();
        warden.setFullName("John Doe");
        warden.setMobileNo("1234567890");
        warden.setPermanentAddress("123 Main St");

       
        Warden savedWarden = wardenRepo.save(warden);

       
        assertNotNull(savedWarden.getId());

       
        Warden retrievedWarden = wardenRepo.findById(savedWarden.getId()).orElse(null);

        
        assertNotNull(retrievedWarden);

        
        assertEquals("John Doe", retrievedWarden.getFullName());
        assertEquals("1234567890", retrievedWarden.getMobileNo());
        assertEquals("123 Main St", retrievedWarden.getPermanentAddress());
    }
}