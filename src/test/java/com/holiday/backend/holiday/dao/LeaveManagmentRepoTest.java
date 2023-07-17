package com.holiday.backend.holiday.dao;
import com.holiday.backend.holiday.model.LeaveManagment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Date;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LeaveManagmentRepoTest {

    @Autowired
    private LeaveManagmentRepo leaveManagmentRepo;

    @Test
    public void testFindAllByStudentId() {
        
        LeaveManagment leaveManagment1 = new LeaveManagment("S123", new Date(), new Date(), "Reason 1", new Date(), "Admin", new Date(), "Pending");
        LeaveManagment leaveManagment2 = new LeaveManagment("S456", new Date(), new Date(), "Reason 2", new Date(), "Admin", new Date(), "Approved");
        leaveManagmentRepo.save(leaveManagment1);
        leaveManagmentRepo.save(leaveManagment2);

       
        List<LeaveManagment> leaveManagements = leaveManagmentRepo.findAllByStudentId("S123");

        
        for (LeaveManagment leaveManagment : leaveManagements) {
            assertEquals("S123", leaveManagment.getStudentId());
        }
    }

    @Test
    public void testFindAllByStatus() {
       
        LeaveManagment leaveManagment1 = new LeaveManagment("S123", new Date(), new Date(), "Reason 1", new Date(), "Admin", new Date(), "Pending");
        LeaveManagment leaveManagment2 = new LeaveManagment("S456", new Date(), new Date(), "Reason 2", new Date(), "Admin", new Date(), "Approved");
        leaveManagmentRepo.save(leaveManagment1);
        leaveManagmentRepo.save(leaveManagment2);

       
        List<LeaveManagment> leaveManagements = leaveManagmentRepo.findAllByStatus("Pending");


        assertEquals(15, leaveManagements.size());
        
    }

    @Test
    public void testFindAllByStudentIdAndStatus() {
        
        LeaveManagment leaveManagment1 = new LeaveManagment("S123", new Date(), new Date(), "Reason 1", new Date(), "Admin", new Date(), "Pending");
        LeaveManagment leaveManagment2 = new LeaveManagment("S456", new Date(), new Date(), "Reason 2", new Date(), "Admin", new Date(), "Approved");
        leaveManagmentRepo.save(leaveManagment1);
        leaveManagmentRepo.save(leaveManagment2);

       
        List<LeaveManagment> leaveManagements = leaveManagmentRepo.findAllByStudentIdAndStatus("S123", "Pending");

        
        for (LeaveManagment leaveManagment : leaveManagements) {
            assertEquals("S123", leaveManagment.getStudentId());
            assertEquals("Pending", leaveManagment.getStatus());
        }
    }
}