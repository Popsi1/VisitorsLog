package com.example.logs.controllers;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.models.Staff;
import com.example.logs.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/staff")
    public ResponseEntity<String> addNewStaffController(@RequestBody SignupRequestDto signupRequestDto){
        Staff staff = Staff.from(signupRequestDto);
        String response = staffService.addNewStaff(staff);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/staffs")
    public ResponseEntity<List<ServiceResponseDto>> getAllStaffController(){
        List<ServiceResponseDto> response = staffService.getAllStaff();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<ServiceResponseDto> getStaffController(@PathVariable Long id){
        Staff staff = staffService.getStaff(id);
        ServiceResponseDto serviceResponseDto = ServiceResponseDto.fromStaff(staff);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.OK);
    }
}
