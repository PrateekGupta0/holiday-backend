package com.holiday.backend.holiday.dao;
import com.holiday.backend.holiday.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepoTest {

    @Autowired
    private StudentRepo studentRepo;

    @Test
    public void testFindByStudentId() {
       
        Student student = new Student();
        student.setStudentId("S123");
        studentRepo.save(student);

      
        Student foundStudent = studentRepo.findByStudentId("S123");

       
        assertNotNull(foundStudent);

        assertEquals("S123", foundStudent.getStudentId());
    }
}
