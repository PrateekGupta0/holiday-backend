package com.holiday.backend.holiday.configuration;

import com.holiday.backend.holiday.dao.LeaveManagmentRepo;
import com.holiday.backend.holiday.dao.StudentRepo;
import com.holiday.backend.holiday.dao.UserTableRepo;
import com.holiday.backend.holiday.dao.WardenRepo;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

@Configuration
public class RepositoryConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public LeaveManagmentRepo leaveManagmentRepo() {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);
        return factory.getRepository(LeaveManagmentRepo.class);
    }

    @Bean
    public StudentRepo studentRepo() {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);
        return factory.getRepository(StudentRepo.class);
    }

    @Bean
    public UserTableRepo userTableRepo() {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);
        return factory.getRepository(UserTableRepo.class);
    }

    @Bean
    public WardenRepo wardenRepo() {
        JpaRepositoryFactory factory = new JpaRepositoryFactory(entityManager);
        return factory.getRepository(WardenRepo.class);
    }
}
