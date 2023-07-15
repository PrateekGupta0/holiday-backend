package com.holiday.backend.holiday.dao;

import com.holiday.backend.holiday.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTableRepo extends JpaRepository<UserTable,Integer>{
    UserTable findByUserId(String username);
}
