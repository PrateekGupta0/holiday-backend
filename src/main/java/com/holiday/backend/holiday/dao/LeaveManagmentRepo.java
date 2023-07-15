package com.holiday.backend.holiday.dao;

import com.holiday.backend.holiday.model.LeaveManagment;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public interface LeaveManagmentRepo extends JpaRepository<LeaveManagment,Integer> {

    public List<LeaveManagment> findAllByStudentId(String studentId);
    public List<LeaveManagment> findAllByStatus(Boolean status);

    public List<LeaveManagment> findAllByStudentIdAndStatus(String studentId,boolean status);
//    Select * from TableName where studentId =" token " && status = "";
}
