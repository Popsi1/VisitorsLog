package com.example.logs.services;

import com.example.logs.constant.Constants;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.dtos.VisitorLogsDto;
import com.example.logs.enums.UserRole;
import com.example.logs.models.User;
import com.example.logs.repositories.UserRepository;
import com.example.logs.repositories.VisitorLogsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.example.logs.constant.Constants.LOG_CREATED;
import static com.example.logs.constant.Constants.SIGNUP;
import static com.example.logs.enums.UserRole.STAFF;
import static com.example.logs.enums.UserRole.VISITOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private VisitorLogsRepository visitorLogsRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private VisitorService visitorService;
//    @Autowired
//    private ModelMapper mapper;

    private User visitor;
    private User staff;

    @BeforeEach
    void setUp() {
        visitor = new User();
        visitor.setRole(VISITOR);
        visitor.setPassword("password");
        staff = new User();
        staff.setRole(STAFF);
    }

    @Test
    void addNewVisitor() {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        when(modelMapper.map(signupRequestDto, User.class)).thenReturn(visitor);
        String value = visitorService.addNewVisitor(signupRequestDto);
        assertThat(value)
                .isNotNull()
                .isEqualTo(SIGNUP);
    }

    @Test
    void getAllVisitors() {
        List<User> visitors = List.of(visitor);
        when(userRepository.findUsersByRole(VISITOR)).thenReturn(visitors);
        List<ServiceResponseDto> serviceResponseDtos = visitorService.getAllVisitors();
        assertThat(serviceResponseDtos).isNotNull();
        assertThat(serviceResponseDtos.size()).isEqualTo(visitors.size());
    }

    @Test
    void getVisitor() {
        long visitorId = 1;
        when(userRepository.findById(visitorId)).thenReturn(Optional.of(visitor));
        ServiceResponseDto responseDto = new ServiceResponseDto();
        when(modelMapper.map(visitor, ServiceResponseDto.class)).thenReturn(responseDto);
        ServiceResponseDto serviceResponseDto = visitorService.getVisitor(visitorId);
        assertThat(serviceResponseDto).isNotNull();
    }

    @Test
    void logsNewVisit() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("ony@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);
        Mockito.when(userRepository.findUserByUsername(any())).thenReturn(Optional.of(staff));

        when(userRepository.findById(1L)).thenReturn(Optional.of(visitor));
        VisitorLogsDto visitorLogsDto = new VisitorLogsDto();
        visitorLogsDto.setReasonForVisit("Reason");
        String value = visitorService.logsNewVisit(1L, visitorLogsDto);
        assertThat(value)
                .isNotNull()
                .isEqualTo(LOG_CREATED);
    }
}