package com.holiday.backend.holiday.controller;

// public class ControllerTest {
    
// }


import com.holiday.backend.holiday.dao.LeaveManagmentRepo;
import com.holiday.backend.holiday.dao.StudentRepo;
import com.holiday.backend.holiday.dao.UserTableRepo;
import com.holiday.backend.holiday.dao.WardenRepo;
import com.holiday.backend.holiday.model.ActionBody;
import com.holiday.backend.holiday.model.LeaveManagment;
import com.holiday.backend.holiday.model.LoginForm;
import com.holiday.backend.holiday.model.Student;
import com.holiday.backend.holiday.model.StudentLeaveRequest;
import com.holiday.backend.holiday.model.StudentModify;
import com.holiday.backend.holiday.model.UserTable;
import com.holiday.backend.holiday.services.JwtTokenService;

import io.jsonwebtoken.lang.Arrays;
import lombok.With;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @Mock
    private LeaveManagmentRepo leaveManagmentRepo;

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private WardenRepo wardenRepo;

    @Mock
    private UserTableRepo userTableRepo;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private Controller controller;

    @Test
    void testHealth() {
        String expectedResponse = "Server is up and running";
        String actualResponse = controller.health();
        assertEquals(expectedResponse, actualResponse);
    }

    // @Test
    // void testLoginSuccess() {
    
    // UserTable userTable = new UserTable();
    // LoginForm loginForm = new LoginForm();
    // loginForm.setPassword("username");
    // loginForm.setUsername("username");
    // userTable.setUserId("username");
    // userTable.setPassword("password");
    // userTable.setRole("admin");

    // Mockito.when(userTableRepo.findByUserId(Mockito.eq("username"))).thenReturn(userTable);
    // Mockito.when(jwtTokenService.generateToken(Mockito.eq("username"))).thenReturn("token");

    // ResponseEntity<Map<String, Object>> response = controller.login(loginForm);

    // assertEquals(HttpStatus.OK, response.getStatusCode());

    // Mockito.verify(userTableRepo).findByUserId("username");
    // Mockito.verify(jwtTokenService).generateToken("username");
    // }

    @Test
    void testLoginUserNotFound() {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("username");
        loginForm.setPassword("password");

        when(userTableRepo.findByUserId("username")).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = controller.login(loginForm);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));

        verify(userTableRepo).findByUserId("username");
        verifyNoInteractions(jwtTokenService);
    }
    
 
    @Test
    void testLoginIncorrectPassword() {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("username");
        loginForm.setPassword("password");

        UserTable userTable = new UserTable();
        userTable.setUserId("username");
        userTable.setPassword("wrong_password");

        when(userTableRepo.findByUserId("username")).thenReturn(userTable);

        ResponseEntity<Map<String, Object>> response = controller.login(loginForm);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));

        verify(userTableRepo).findByUserId("username");
        verifyNoInteractions(jwtTokenService);
    }

    @Test
    void testMyRequestAll() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(leaveManagmentRepo.findAllByStudentId(anyString())).thenReturn(Collections.singletonList(new LeaveManagment()));

        ResponseEntity<Map<String, Object>> response = controller.myRequest("token", "all");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));
        assertEquals(1, ((LeaveManagment[]) response.getBody().get("data")).length);

        verify(jwtTokenService).parseToken("token");
        verify(leaveManagmentRepo).findAllByStudentId("studentId");
    }

    @Test
    void testMyRequestPending() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(leaveManagmentRepo.findAllByStudentIdAndStatus(anyString(), anyString()))
                .thenReturn(Collections.singletonList(new LeaveManagment()));

        ResponseEntity<Map<String, Object>> response = controller.myRequest("token", "pending");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));
        assertEquals(1, ((LeaveManagment[]) response.getBody().get("data")).length);

        verify(jwtTokenService).parseToken("token");
        verify(leaveManagmentRepo).findAllByStudentIdAndStatus("studentId", "pending");
    }

    @Test
    void testMyRequestApproved() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(leaveManagmentRepo.findAllByStudentIdAndStatus(anyString(), anyString()))
                .thenReturn(Collections.singletonList(new LeaveManagment()));

        ResponseEntity<Map<String, Object>> response = controller.myRequest("token", "approved");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));
        assertEquals(1, ((LeaveManagment[]) response.getBody().get("data")).length);

        verify(jwtTokenService).parseToken("token");
        verify(leaveManagmentRepo).findAllByStudentIdAndStatus("studentId", "approved");
    }

    @Test
    void testMyRequestDenied() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(leaveManagmentRepo.findAllByStudentIdAndStatus(anyString(), anyString()))
                .thenReturn(Collections.singletonList(new LeaveManagment()));

        ResponseEntity<Map<String, Object>> response = controller.myRequest("token", "denied");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));
        assertEquals(1, ((LeaveManagment[]) response.getBody().get("data")).length);

        verify(jwtTokenService).parseToken("token");
        verify(leaveManagmentRepo).findAllByStudentIdAndStatus("studentId", "denied");
    }

    @Test
    void testMyRequestInvalidFilter() {
        ResponseEntity<Map<String, Object>> response = controller.myRequest("token", "invalid");
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    
    @Test
    void testStudentDetailSuccess() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(studentRepo.findByStudentId("studentId")).thenReturn(new Student());

        ResponseEntity<Map<String, Object>> response = controller.oldStudentDetail("token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));
        assertNotNull(response.getBody().get("data"));

        verify(jwtTokenService).parseToken("token");
        verify(studentRepo).findByStudentId("studentId");
    }

    @Test
    void testStudentDetailUnauthorized() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("FAILED");

        ResponseEntity<Map<String, Object>> response = controller.oldStudentDetail("token");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
        

        verify(jwtTokenService).parseToken("token");
        verifyNoInteractions(studentRepo);
    }

    @Test
    void testStudentDetailServerError() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(studentRepo.findByStudentId(anyString())).thenThrow(new RuntimeException());

        ResponseEntity<Map<String, Object>> response = controller.oldStudentDetail("token");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
       

        verify(jwtTokenService).parseToken("token");
        verify(studentRepo).findByStudentId("studentId");
    }

    @Test
    void testModifyStudentSuccess() {
        StudentModify request = new StudentModify();
        request.setName("John Doe");
        request.setPhoneNumber("1234567890");
        request.setAddress("123 Street");

        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(studentRepo.findByStudentId("studentId")).thenReturn(new Student());

        ResponseEntity<Map<String, Object>> response = controller.modifyStudent("token", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));

        verify(jwtTokenService).parseToken("token");
        verify(studentRepo).findByStudentId("studentId");
        verify(studentRepo).save(any(Student.class));
    }

    @Test
    void testModifyStudentUnauthorized() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("FAILED");

        ResponseEntity<Map<String, Object>> response = controller.modifyStudent("token", new StudentModify());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
       

        verify(jwtTokenService).parseToken("token");
        verifyNoInteractions(studentRepo);
    }

    @Test
    void testModifyStudentServerError() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("studentId");
        when(studentRepo.findByStudentId(anyString())).thenReturn(new Student());
        when(studentRepo.save(any(Student.class))).thenThrow(new RuntimeException());

        ResponseEntity<Map<String, Object>> response = controller.modifyStudent("token", new StudentModify());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
        

        verify(jwtTokenService).parseToken("token");
        verify(studentRepo).findByStudentId("studentId");
        verify(studentRepo).save(any(Student.class));
    }

 
    @Test
void testPendingRequestAll() {
    String token = "validToken";

    List<LeaveManagment> leaveManagments = new ArrayList<>();
    LeaveManagment leaveManagment1 = new LeaveManagment();
    LeaveManagment leaveManagment2 = new LeaveManagment();
    leaveManagments.add(leaveManagment1);
    leaveManagments.add(leaveManagment2);

    Mockito.when(jwtTokenService.parseToken(Mockito.eq(token))).thenReturn("wardenId");
    Mockito.when(leaveManagmentRepo.findAllByStatus(Mockito.eq("pending"))).thenReturn(leaveManagments);

    ResponseEntity<Map<String, Object>> response = controller.pendingRequest(token, "");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("SUCCESS", response.getBody().get("message"));
    

    Mockito.verify(jwtTokenService).parseToken(token);
    Mockito.verify(leaveManagmentRepo).findAllByStatus("pending");
}

@Test
void testPendingRequestWithSearch() {
   
    List<LeaveManagment> leaveManagments = new ArrayList<>();
    when(jwtTokenService.parseToken(anyString())).thenReturn("wardenId");
    when(leaveManagmentRepo.findAllByStudentIdAndStatus(anyString(), anyString()))
            .thenReturn(leaveManagments);

    ResponseEntity<Map<String, Object>> response = controller.pendingRequest("token", "search");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("SUCCESS", response.getBody().get("message"));
   
    verify(jwtTokenService).parseToken("token");
    verify(leaveManagmentRepo).findAllByStudentIdAndStatus("search", "pending");
}




    @Test
    void testPendingRequestUnauthorized() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("FAILED");

        ResponseEntity<Map<String, Object>> response = controller.pendingRequest("token", "");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
        

        verify(jwtTokenService).parseToken("token");
        verifyNoInteractions(leaveManagmentRepo);
    }

    @Test
    void testPendingRequestServerError() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("wardenId");
        when(leaveManagmentRepo.findAllByStatus(anyString())).thenThrow(new RuntimeException());

        ResponseEntity<Map<String, Object>> response = controller.pendingRequest("token", "");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
        

        verify(jwtTokenService).parseToken("token");
        verify(leaveManagmentRepo).findAllByStatus("pending");
    }

    @Test
    void testApprovedRequestAll() {

      List<LeaveManagment> l =new ArrayList<>() ;
      
      when(jwtTokenService.parseToken(anyString())).thenReturn("wardenId");
      when(leaveManagmentRepo.findAllByStudentId(anyString()))
            .thenReturn(l);

    ResponseEntity<Map<String, Object>> response = controller.approvedRequest("token", "search");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("SUCCESS", response.getBody().get("message"));
    

    verify(jwtTokenService).parseToken("token");
    verify(leaveManagmentRepo).findAllByStudentId("search");
}

    @Test
    void testApprovedRequestWithSearch() {
        List<LeaveManagment> l =new ArrayList<>() ;
        when(jwtTokenService.parseToken(anyString())).thenReturn("wardenId");
        when(leaveManagmentRepo.findAllByStudentId(anyString()))
                .thenReturn(l);

        ResponseEntity<Map<String, Object>> response = controller.approvedRequest("token", "search");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));
        
        verify(jwtTokenService).parseToken("token");
        verify(leaveManagmentRepo).findAllByStudentId("search");
    }

    @Test
    void testApprovedRequestUnauthorized() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("FAILED");

        ResponseEntity<Map<String, Object>> response = controller.approvedRequest("token", "");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
        

        verify(jwtTokenService).parseToken("token");
        verifyNoInteractions(leaveManagmentRepo);
    }



    @Test
    void testRequestActionApproved() {
        ActionBody actionBody = new ActionBody();
        actionBody.setId("1");
        actionBody.setAction("approved");

        when(jwtTokenService.parseToken(anyString())).thenReturn("wardenId");
        when(leaveManagmentRepo.findById(anyInt())).thenReturn(Optional.of(new LeaveManagment()));
        when(leaveManagmentRepo.save(any(LeaveManagment.class))).thenReturn(new LeaveManagment());

        ResponseEntity<Map<String, Object>> response = controller.requestAction("token", actionBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().get("message"));
       

        verify(jwtTokenService).parseToken("token");
        verify(leaveManagmentRepo).findById(1);
        verify(leaveManagmentRepo).save(any(LeaveManagment.class));
    }

    @Test
void testRequestActionDenied() {
    when(jwtTokenService.parseToken(anyString())).thenReturn("wardenId");
    when(leaveManagmentRepo.findById(anyInt())).thenReturn(Optional.of(new LeaveManagment()));
    
    ActionBody acc=new ActionBody();
    acc.setId("1");
    acc.setAction( "denied");

    ResponseEntity<Map<String, Object>> response = controller.requestAction("token", acc);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("SUCCESS", response.getBody().get("message"));

    verify(jwtTokenService).parseToken("token");
    verify(leaveManagmentRepo).findById(1);
    verify(leaveManagmentRepo).save(any(LeaveManagment.class)); // Ensure save method is invoked
}

    @Test
    void testRequestActionUnauthorized() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("FAILED");

        ResponseEntity<Map<String, Object>> response = controller.requestAction("token", new ActionBody());

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("FAILED", response.getBody().get("message"));
        

        verify(jwtTokenService).parseToken("token");
        verifyNoInteractions(leaveManagmentRepo);
    }

    @Test
    void testRequestActionServerError() {
        when(jwtTokenService.parseToken(anyString())).thenReturn("wardenId");

        ResponseEntity<Map<String, Object>> response = controller.requestAction("token", new ActionBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        
        verify(jwtTokenService).parseToken("token");
    }
}