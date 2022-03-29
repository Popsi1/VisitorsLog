package com.example.logs.controllers;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.dtos.VisitorLogsDto;
import com.example.logs.services.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visitor")
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping("/add-new-visitor")
    public ResponseEntity<String> addNewVisitor(@RequestBody SignupRequestDto signupRequestDto){
        String response = visitorService.addNewVisitor(signupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/all-visitors")
    public ResponseEntity<List<ServiceResponseDto>> getAllVisitors(){
        List<ServiceResponseDto> response = visitorService.getAllVisitors();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/visitor/{id}")
    public ResponseEntity<ServiceResponseDto> getVisitor(@PathVariable Long id){
        ServiceResponseDto serviceResponseDto = visitorService.getVisitor(id);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.OK);
    }

    @PostMapping("/visit/{visitorId}")
    public ResponseEntity<String> visitController(@PathVariable long visitorId, @RequestBody VisitorLogsDto visitorLogsDto){
        String response = visitorService.logsNewVisit(visitorId, visitorLogsDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
