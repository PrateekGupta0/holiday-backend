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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
public class Controller {


    @Autowired
    LeaveManagmentRepo leaveManagmentRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    WardenRepo wardenRepo;





    @Autowired
    UserTableRepo userTableRepo;



    @Autowired
    private  JwtTokenService jwtTokenService;

//    @Autowired
//    public Controller(JwtTokenService jwtTokenService) {
//        this.jwtTokenService = jwtTokenService;
//    }

    @RequestMapping(method = RequestMethod.GET ,value = "/health")
    public String health(){
        return "Server is up and running";
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

        //Password Check
        String userPassword=userTable.getPassword();
        String loginPassword=loginForm.getPassword();
        if(!userPassword.equals(loginPassword)){
            Map<String,Object> map=new HashMap<>();
            map.put("data","null");
            map.put("message","FAILED");
            return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
        }

        // Generate the JWT token
        String token = jwtTokenService.generateToken(loginForm.getUsername());

        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("username",loginForm.getUsername());
        map.put("role",userTable.getRole());
        map.put("message","SUCCESS");

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/student")
    public ResponseEntity<?> leaveRequest(@RequestHeader("token") String token,@RequestBody StudentLeaveRequest details){


        // Access the claims as needed
        String userId = jwtTokenService.parseToken(token);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        LocalDate startDate=LocalDate.parse(details.getStartDate(),formatter);
        LocalDate endDate=LocalDate.parse(details.getEndDate(),formatter);
        String reason=details.getReason();
        LocalDate currentDate = LocalDate.now();

        if(startDate.isAfter(endDate) || startDate.isBefore(currentDate)){
            // return enddate is smaller then startDate.
            Map<String,String> res=new HashMap<>();
            res.put("data","null");
            res.put("message","FAILED");
            return  new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
        java.util.Date timestamp = new Timestamp(System.currentTimeMillis());
        LeaveManagment leaveManagment=new LeaveManagment(userId,Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),reason,timestamp,"student",timestamp,"pending");



        try{
            LeaveManagment res=leaveManagmentRepo.save(leaveManagment);
        }
        catch (Exception e){
            Map<String,String> res=new HashMap<>();
            res.put("data","null");
            res.put("message","FAILED");
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String,String> res=new HashMap<>();
        res.put("data","null");
        res.put("message","SUCCESS");
        return  new ResponseEntity<>(res,HttpStatus.OK);


    }

    @RequestMapping(method = RequestMethod.GET,value = "/student-request")
    public ResponseEntity<Map<String,Object>> myRequest(@RequestHeader("token") String token,@RequestParam(value="filter") String req){


        // Access the claims as needed
        String studentId = jwtTokenService.parseToken(token);


        List<LeaveManagment> res;
        Map<String,Object> response=new HashMap<>();

        if(studentId == "FAILED"){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        if(req.equals("all")){
//            System.out.println("Entered");
            try {
                //need to change
                res = leaveManagmentRepo.findAllByStudentId(studentId);
//                System.out.println(res.size());
                LeaveManagment[] arrlist=res.toArray(res.toArray(new LeaveManagment[0]));
//                System.out.println(arrlist.toString());
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
        else if(req.equals("pending")){
            try {
                //need to change.
                res = leaveManagmentRepo.findAllByStudentIdAndStatus(studentId,"pending");
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
        else if(req.equals("approved")){
            try {
                // need to change
                res = leaveManagmentRepo.findAllByStudentIdAndStatus(studentId,"approved");
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
        else{
            try {
                // need to change
                res = leaveManagmentRepo.findAllByStudentIdAndStatus(studentId,"denied");
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

    @RequestMapping(method = RequestMethod.GET ,value = "/student-detail")
    public ResponseEntity<Map<String,Object>> oldStudentDetail(@RequestHeader("token") String token){

        // Access the claims as needed
        String studentId = jwtTokenService.parseToken(token);

        Map<String,Object> map=new HashMap<>();
        if(studentId == "FAILED"){
            map.put("data","null");
            map.put("message","FAILED");
            return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
        }

        try{
            Student student= studentRepo.findByStudentId(studentId);

            Map<String,String> map1=new HashMap<>();
            map1.put("name",student.getFullName());
            map1.put("phoneNumber",student.getMobileNo());
            map1.put("address",student.getPermanentAddress());
            map.put("data",map1);
            map.put("message","SUCCESS");

        }
        catch (Exception e){
            // Internal server error.
            map.put("data","null");
            map.put("message","FAILED");
            return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT ,value = "/student-detail")
    public ResponseEntity modifyStudent(@RequestHeader("token") String token,@RequestBody StudentModify req){


        // Access the claims as needed
        String studentId = jwtTokenService.parseToken(token);


        Map<String,String> map=new HashMap<>();
        if(studentId == "FAILED"){
            map.put("data","null");
            map.put("message","FAILED");
            return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
        }
        try{
            //Id needed.
            Student student= studentRepo.findByStudentId(studentId);
            // Make the modifications to the entity
            student.setFullName(req.getName());
            student.setMobileNo(req.getPhoneNumber());
            student.setPermanentAddress(req.getAddress());

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
    public ResponseEntity<Map<String,Object>> pendingRequest(@RequestHeader("token") String token,@RequestParam(value = "search") String search){


        // Access the claims as needed
        String wardenId = jwtTokenService.parseToken(token);


        List<LeaveManagment> res;
        Map<String,Object> response=new HashMap<>();
        if(wardenId == "FAILED"){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        if(search.equals("")){
            try {
                //need to change.
                res = leaveManagmentRepo.findAllByStatus("pending");
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
        }
        else{
            try {
                //need to change.
                res = leaveManagmentRepo.findAllByStudentIdAndStatus(search,"pending");
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
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/all-request")
    public ResponseEntity<Map<String,Object>> approvedRequest(@RequestHeader("token") String token,@RequestParam(value = "search") String search){


        // Access the claims as needed
        String wardenId = jwtTokenService.parseToken(token);

        List<LeaveManagment> res;
        Map<String,Object> response=new HashMap<>();

        if(wardenId == "FAILED"){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
        if(search.equals("")){
            try {
                //need to change.
                res = leaveManagmentRepo.findAll();
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
        }
        else{
            try {
                //need to change.
                res = leaveManagmentRepo.findAllByStudentId(search);
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
        }


        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT ,value = "/pending")
    public ResponseEntity<Map<String,Object>> requestAction(@RequestHeader("token") String token,@RequestBody ActionBody actionBody){
        // Access the claims as needed
        String wardenId = jwtTokenService.parseToken(token);

        Optional<LeaveManagment> res;
        Map<String,Object> response=new HashMap<>();

        if(wardenId == "FAILED"){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }

        try{
            int number = Integer.parseInt(actionBody.getId());
            res=leaveManagmentRepo.findById(number);
            LeaveManagment temp=res.get();

            String action= actionBody.getAction();
            if(action.equals("approved")){
                temp.setStatus("approved");
            }
            else{
                temp.setStatus("denied");
            }

            leaveManagmentRepo.save(temp);
        }
        catch (Exception e){
            response.put("data","null");
            response.put("message","FAILED");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("data","null");
        response.put("message","SUCCESS");
        return new ResponseEntity(response,HttpStatus.OK);
    }

}
