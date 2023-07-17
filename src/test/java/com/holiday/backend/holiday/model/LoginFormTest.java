package com.holiday.backend.holiday.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginFormTest {

    @Test
    public void testLoginForm() {
        
        LoginForm loginForm = new LoginForm();
        
      
        loginForm.setUsername("testuser");
        loginForm.setPassword("password123");
        
       
        assertEquals("testuser", loginForm.getUsername());
        assertEquals("password123", loginForm.getPassword());
    }
}