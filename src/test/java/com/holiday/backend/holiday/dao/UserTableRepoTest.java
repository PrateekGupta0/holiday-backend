package com.holiday.backend.holiday.dao;
// import com.holiday.backend.holiday.dao.UserTableRepo;
import com.holiday.backend.holiday.model.UserTable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTableRepoTest {

    @Autowired
    private UserTableRepo userTableRepo;

    @Test
    public void testFindByUserId() {
      
        UserTable user = new UserTable();
        user.setUserId("user123");
        userTableRepo.save(user);

       
        UserTable foundUser = userTableRepo.findByUserId("user123");

       
        assertNotNull(foundUser);

     
        assertEquals("user123", foundUser.getUserId());
    }
}