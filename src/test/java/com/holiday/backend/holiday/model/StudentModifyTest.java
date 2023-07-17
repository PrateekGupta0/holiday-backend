package com.holiday.backend.holiday.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentModifyTest {

    @Test
    public void testStudentModify() {
        
        StudentModify studentModify = new StudentModify();
        
       
        studentModify.setName("John Doe");
        studentModify.setPhoneNumber("1234567890");
        studentModify.setAddress("123 Main St");
        
      
        assertEquals("John Doe", studentModify.getName());
        assertEquals("1234567890", studentModify.getPhoneNumber());
        assertEquals("123 Main St", studentModify.getAddress());
    }
}