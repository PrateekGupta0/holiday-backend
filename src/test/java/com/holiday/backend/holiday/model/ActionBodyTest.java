package com.holiday.backend.holiday.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionBodyTest {

    @Test
    public void testActionBody() {
        
        ActionBody actionBody = new ActionBody();
        
        
        actionBody.setId("123");
        actionBody.setAction("update");
        
        
        assertEquals("123", actionBody.getId());
        assertEquals("update", actionBody.getAction());
    }
}