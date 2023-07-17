package com.holiday.backend.holiday.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentLeaveRequestTest {

    @Test
    public void testStudentLeaveRequest() {
       
        StudentLeaveRequest studentLeaveRequest = new StudentLeaveRequest();
        
        
        studentLeaveRequest.setStartDate("2023-07-16");
        studentLeaveRequest.setEndDate("2023-07-20");
        studentLeaveRequest.setReason("Vacation");
        
      
        assertEquals("2023-07-16", studentLeaveRequest.getStartDate());
        assertEquals("2023-07-20", studentLeaveRequest.getEndDate());
        assertEquals("Vacation", studentLeaveRequest.getReason());
    }
}
