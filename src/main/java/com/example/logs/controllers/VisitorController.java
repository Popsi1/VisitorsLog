package com.example.logs.controllers;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.dtos.VisitorLogsDto;
import com.example.logs.models.Visitor;
import com.example.logs.models.VisitorLogs;
import com.example.logs.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @PostMapping("/visitor")
    public ResponseEntity<String> addNewVisitorController(@RequestBody SignupRequestDto signupRequestDto){
        Visitor visitor = Visitor.from(signupRequestDto);
        String response = visitorService.addNewVisitor(visitor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/visitors")
    public ResponseEntity<List<ServiceResponseDto>> getAllStaffController(){
        List<ServiceResponseDto> response = visitorService.getAllVisitors();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/visitor/{id}")
    public ResponseEntity<ServiceResponseDto> getStaffController(@PathVariable Long id){
        Visitor visitor = visitorService.getVisitor(id);
        ServiceResponseDto serviceResponseDto = ServiceResponseDto.fromVisitor(visitor);
        return new ResponseEntity<>(serviceResponseDto, HttpStatus.OK);
    }

    @PostMapping("/visit/{visitorId}/staff/{staffId}")
    public ResponseEntity<String> visitController(@PathVariable Long visitorId, @PathVariable Long staffId, @RequestBody VisitorLogsDto visitorLogsDto){
        VisitorLogs visitorLogs = VisitorLogs.from(visitorLogsDto);
        String response = visitorService.logsNewVisit(visitorId,staffId,visitorLogs);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
