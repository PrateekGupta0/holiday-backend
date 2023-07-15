package com.holiday.backend.holiday.dao;

import com.holiday.backend.holiday.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

//@Component
public interface UserTableRepo extends JpaRepository<UserTable,Integer>{
    UserTable findByUserId(String username);
}
