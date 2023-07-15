package com.holiday.backend.holiday.dao;

import com.holiday.backend.holiday.model.LeaveManagment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveManagmentRepo extends JpaRepository<LeaveManagment,Integer> {

    public List<LeaveManagment> findAllByStudentId(String studentId);
    public List<LeaveManagment> findAllByStatus(String status);

    public List<LeaveManagment> findAllByStudentIdAndStatus(String studentId,String status);
//    Select * from TableName where studentId =" token " && status = "";
}
