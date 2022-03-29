package com.example.logs.services;

import com.example.logs.constant.Constants;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.dtos.VisitorLogsDto;
import com.example.logs.exceptions.StaffNotFoundException;
import com.example.logs.exceptions.VisitorNotFoundException;
import com.example.logs.models.User;
import com.example.logs.models.VisitorLogs;
import com.example.logs.repositories.UserRepository;
import com.example.logs.repositories.VisitorLogsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.logs.enums.UserRole.VISITOR;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final VisitorLogsRepository visitorLogsRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public String addNewVisitor(SignupRequestDto signupRequestDto) {
        userService.checkIfUsernameOrEmailAlreadyExist(signupRequestDto);
        User newVisitor = modelMapper.map(signupRequestDto, User.class);
        newVisitor.setPassword(passwordEncoder.encode(newVisitor.getPassword()));
        newVisitor.setRole(VISITOR);
        userRepository.save(newVisitor);

        return Constants.SIGNUP;
    }

    public List<ServiceResponseDto> getAllVisitors(){
        List<ServiceResponseDto> listOfAllVisitors = new ArrayList<>();
        List<User> allVisitorsSaved = userRepository.findUsersByRole(VISITOR);

        for (User visitor : allVisitorsSaved) {
            ServiceResponseDto responseDto = modelMapper.map(visitor, ServiceResponseDto.class);
            listOfAllVisitors.add(responseDto);
        }

        return listOfAllVisitors;
    }

    public ServiceResponseDto getVisitor(Long visitorId){
        User visitor = userRepository.findById(visitorId).orElseThrow(() ->
                new VisitorNotFoundException("visitor not found"));
        return modelMapper.map(visitor, ServiceResponseDto.class);
    }

    public String logsNewVisit(Long visitorId, VisitorLogsDto visitorLogs){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User staff = userRepository.findUserByUsername(username).orElseThrow(()->
                new StaffNotFoundException("Staff not found"));

        User visitor = userRepository.findById(visitorId).orElseThrow(()->
                new VisitorNotFoundException("Visitor not found"));

        VisitorLogs newVisitLog = new VisitorLogs();
        newVisitLog.setVisitor(visitor);
        newVisitLog.setStaff(staff);
        newVisitLog.setReasonForVisit(visitorLogs.getReasonForVisit());
        newVisitLog.setDateOfVisit(Date.valueOf(LocalDate.now()));
        visitorLogsRepository.save(newVisitLog);

        emailService.send(staff.getEmail(), visitor.getName() + Constants.BODY + newVisitLog.getDateOfVisit().toString(), Constants.SUBJECT);

        return Constants.LOG_CREATED;
    }
}
