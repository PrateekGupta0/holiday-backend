package com.holiday.backend.holiday.controller;


import com.holiday.backend.holiday.dao.LeaveManagmentRepo;
import com.holiday.backend.holiday.dao.StudentRepo;
import com.holiday.backend.holiday.dao.UserTableRepo;
import com.holiday.backend.holiday.dao.WardenRepo;
import com.holiday.backend.holiday.model.*;
import com.holiday.backend.holiday.services.JwtTokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {


    @Autowired
    LeaveManagmentRepo leaveManagmentRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    WardenRepo wardenRepo;

    @Autowired
    LeaveManagment leaveManagment;

    @Autowired
    Student student;

    @Autowired
    UserTableRepo userTableRepo;




    private final JwtTokenService jwtTokenService;

    @Autowired
    public Controller(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }


    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody LoginForm loginForm) {

        UserTable userTable= userTableRepo.findByUserId(loginForm.getUsername());
        if(userTable == null){
            Map<String,Object> map=new HashMap<>();
            map.put("data","null");
            map.put("message","FAILED");
            return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);

        }

        // Generate the JWT token
        String token = JwtTokenService.generateToken(loginForm.getUsername());

        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("username",loginForm.getUsername());
        map.put("role",userTable.getRole());
        map.put("message","SUCCESS");

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/student")
    public ResponseEntity leaveRequest(@RequestHeader("token") String token,@RequestBody StudentLeaveRequest details){


        // Access the claims as needed
        String userId = JwtTokenService.parseToken(token);

        LocalDate startDate=LocalDate.parse(details.getStartDate());
        LocalDate endDate=LocalDate.parse(details.getEndDate());
        String reason=details.getReason();

        if(startDate.isAfter(endDate)){
            // return enddate is smaller then startDate.
            Map<String,String> res=new HashMap<>();
            res.put("data","null");
            res.put("message","FAILED");
            return  new ResponseEntity(res,HttpStatus.BAD_REQUEST);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        leaveManagment=new LeaveManagment(userId,Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),reason,timestamp,"null",timestamp);



        try{
            LeaveManagment res=leaveManagmentRepo.save(leaveManagment);
        }
        catch (Exception e){
            Map<String,String> res=new HashMap<>();
            res.put("data","null");
            res.put("message","FAILED");
            return new ResponseEntity(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String,String> res=new HashMap<>();
        res.put("data","null");
        res.put("message","SUCCESS");
        return  new ResponseEntity(res,HttpStatus.OK);


    }

    @RequestMapping(method = RequestMethod.GET,value = "/student-Request")
    public ResponseEntity<Map<String,Object>> myRequest(@RequestHeader("token") String token,@RequestParam(value="filter") String req){


        // Access the claims as needed
        String studentId = JwtTokenService.parseToken(token);


        List<LeaveManagment> res;
        Map<String,Object> response=new HashMap<>();

        if(studentId == "FAILED"){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        if(req == "all"){
            try {
                //need to change
                res = leaveManagmentRepo.findAllByStudentId(studentId);
                LeaveManagment[] arrlist=res.toArray(res.toArray(new LeaveManagment[0]));
                response.put("data",arrlist);
                response.put("message","SUCCESS");
            }
            catch (Exception e){
//                LeaveManagment[] arrlist = new LeaveManagment[0];
                response.put("data","null");
                response.put("message","FAILED");
                return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else if(req == "pending"){
            try {
                //need to change.
                res = leaveManagmentRepo.findAllByStudentIdAndStatus(studentId,"false");
                LeaveManagment[] arrlist=res.toArray(res.toArray(new LeaveManagment[0]));
                response.put("data",arrlist);
                response.put("message","SUCCESS");
            }
            catch (Exception e){
//                LeaveManagment[] arrlist = new LeaveManagment[0];
                response.put("data","null");
                response.put("message","FAILED");
                return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else if(req == "approved"){
            try {
                // need to change
                res = leaveManagmentRepo.findAllByStudentIdAndStatus(studentId,"true");
                LeaveManagment[] arrlist=res.toArray(res.toArray(new LeaveManagment[0]));
                response.put("data",arrlist);
                response.put("message","SUCCESS");
            }
            catch (Exception e){
//                LeaveManagment[] arrlist = new LeaveManagment[0];
                response.put("data","null");
                response.put("message","FAILED");
                return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT ,value = "/student-Modify")
    public ResponseEntity modifyStudent(@RequestHeader("token") String token,@RequestBody StudentModify req){


        // Access the claims as needed
        String studentId = JwtTokenService.parseToken(token);


        Map<String,String> map=new HashMap<>();
        if(studentId == "FAILED"){
            map.put("data","null");
            map.put("message","FAILED");
            return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
        }
        try{
            //Id needed.
            student= studentRepo.findByStudentId(studentId);
            // Make the modifications to the entity
            student.setFullName(req.getName());
            student.setMobileNo(req.getMobileNo());
            student.setPermanentAddress(req.getPermanentAddress());

            // Save the modified entity back to the database
            studentRepo.save(student);
        }
        catch (Exception e){
            // Internal server error.
            map.put("data","null");
            map.put("message","FAILED");
            return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("data","null");
        map.put("message","SUCCESS");
        return new ResponseEntity(map,HttpStatus.OK);

    }


    @RequestMapping(method = RequestMethod.GET,value = "/pending")
    public ResponseEntity<Map<String,Object>> pendingRequest(@RequestHeader("token") String token){


        // Access the claims as needed
        String studentId = JwtTokenService.parseToken(token);


        List<LeaveManagment> res;
        Map<String,Object> response=new HashMap<>();
        if(studentId == "FAILED"){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        try {
            //need to change.
            res = leaveManagmentRepo.findAllByStatus("false");
            LeaveManagment[] arrlist=res.toArray(res.toArray(new LeaveManagment[0]));
            response.put("data",arrlist);
            response.put("message","SUCCESS");
        }
        catch (Exception e){
//            LeaveManagment[] arrlist = new LeaveManagment[0];
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/approved")
    public ResponseEntity<Map<String,Object>> approvedRequest(@RequestHeader("token") String token){


        // Access the claims as needed
        String studentId = JwtTokenService.parseToken(token);

        List<LeaveManagment> res;
        Map<String,Object> response=new HashMap<>();

        if(studentId == "FAILED"){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        try {
            //need to change.
            res = leaveManagmentRepo.findAllByStatus("true");
            LeaveManagment[] arrlist=res.toArray(res.toArray(new LeaveManagment[0]));
            response.put("data",arrlist);
            response.put("message","SUCCESS");
        }
        catch (Exception e){
//            LeaveManagment[] arrlist = new LeaveManagment[0];
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
