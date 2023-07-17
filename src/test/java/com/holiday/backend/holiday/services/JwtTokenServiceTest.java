package com.holiday.backend.holiday.services;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenServiceTest {
    
    JwtTokenService jwtTokenService=new JwtTokenService();
    @Test
    public void testGenerateAndParseToken() {
        String userId = "123456";
       
    
    
        
        String token = jwtTokenService.generateToken(userId);
        
        
        assertNotNull(token);
        
        
        String parsedUserId = jwtTokenService.parseToken(token);
        
        
        assertEquals(userId, parsedUserId);
    }

    @Test
    public void testParseInvalidToken() {
        String invalidToken = "invalid_token";
        String parsedUserId = jwtTokenService.parseToken(invalidToken);
        
        assertEquals("FAILED", parsedUserId);
    }
}