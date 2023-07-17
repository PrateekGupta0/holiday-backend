package com.holiday.backend.holiday.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class StudentTest {

    @Test
    public void testStudent() {
        
        Student student = new Student();
        
        
        student.setId(1);
        student.setFullName("John Doe");
        student.setBatch("2023");
        student.setDivision("A");
        student.setRollNo("123");
        student.setMobileNo("1234567890");
        student.setPermanentAddress("123 Main St");
        student.setLastUpdated(new Date());
        student.setUpdatedBy("Admin");
        student.setCreateOn(new Date());
        student.setStudentId("S12345");
        
        
        assertEquals(1, student.getId());
        assertEquals("John Doe", student.getFullName());
        assertEquals("2023", student.getBatch());
        assertEquals("A", student.getDivision());
        assertEquals("123", student.getRollNo());
        assertEquals("1234567890", student.getMobileNo());
        assertEquals("123 Main St", student.getPermanentAddress());
        assertNotNull(student.getLastUpdated());
        assertEquals("Admin", student.getUpdatedBy());
        assertNotNull(student.getCreateOn());
        assertEquals("S12345", student.getStudentId());
    }
}