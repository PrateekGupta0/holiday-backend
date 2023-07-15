package com.holiday.backend.holiday.dao;

import com.holiday.backend.holiday.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

//@Component
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Student findByStudentId(String student);
}
