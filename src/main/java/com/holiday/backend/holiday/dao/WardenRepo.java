package com.holiday.backend.holiday.dao;

import com.holiday.backend.holiday.model.Warden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

//@Component
public interface WardenRepo extends JpaRepository<Warden,Integer> {

}
