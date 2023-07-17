package com.holiday.backend.holiday.model;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class LeaveManagementTest {

    @Test
    public void testLeaveManagmentConstructorAndGetters() {
       
        String studentId = "S12345";
        Date startDate = new Date();
        Date endDate = new Date();
        String reason = "Vacation";
        Date lastUpdated = new Date();
        String updatedBy = "John Doe";
        Date createdOn = new Date();
        String status = "Pending";

        LeaveManagment leaveManagment = new LeaveManagment(studentId, startDate, endDate, reason,
                lastUpdated, updatedBy, createdOn, status);

        
        assertEquals(studentId, leaveManagment.getStudentId());
        assertEquals(startDate, leaveManagment.getStartDate());
        assertEquals(endDate, leaveManagment.getEndDate());
        assertEquals(reason, leaveManagment.getReason());
        assertEquals(lastUpdated, leaveManagment.getLastUpdated());
        assertEquals(updatedBy, leaveManagment.getUpdatedBy());
        assertEquals(createdOn, leaveManagment.getCreatedOn());
        assertEquals(status, leaveManagment.getStatus());
    }

    @Test
    public void testLeaveManagmentSetters() {
       
        LeaveManagment leaveManagment = new LeaveManagment();

       
        String studentId = "S12345";
        Date startDate = new Date();
        Date endDate = new Date();
        String reason = "Vacation";
        Date lastUpdated = new Date();
        String updatedBy = "John Doe";
        Date createdOn = new Date();
        String status = "Pending";

        leaveManagment.setStudentId(studentId);
        leaveManagment.setStartDate(startDate);
        leaveManagment.setEndDate(endDate);
        leaveManagment.setReason(reason);
        leaveManagment.setLastUpdated(lastUpdated);
        leaveManagment.setUpdatedBy(updatedBy);
        leaveManagment.setCreatedOn(createdOn);
        leaveManagment.setStatus(status);

       
        assertEquals(studentId, leaveManagment.getStudentId());
        assertEquals(startDate, leaveManagment.getStartDate());
        assertEquals(endDate, leaveManagment.getEndDate());
        assertEquals(reason, leaveManagment.getReason());
        assertEquals(lastUpdated, leaveManagment.getLastUpdated());
        assertEquals(updatedBy, leaveManagment.getUpdatedBy());
        assertEquals(createdOn, leaveManagment.getCreatedOn());
        assertEquals(status, leaveManagment.getStatus());
    }
}
