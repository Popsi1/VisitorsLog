package com.example.logs.controllers;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PostMapping("/add-new-staff")
    public ResponseEntity<String> addNewStaff(@Valid @RequestBody SignupRequestDto signupRequestDto){
        String response = staffService.addNewStaff(signupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/all-staffs")
    public ResponseEntity<List<ServiceResponseDto>> getAllStaffController(){
        List<ServiceResponseDto> response = staffService.getAllStaff();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/staff/{id}")
    public ResponseEntity<ServiceResponseDto> getStaffController(@PathVariable Long id){
        ServiceResponseDto serviceResponseDto = staffService.getStaff(id);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.OK);
    }
}
