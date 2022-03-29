package com.example.logs.services;

import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.models.User;
import com.example.logs.repositories.UserRepository;
import com.example.logs.repositories.VisitorLogsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.example.logs.constant.Constants.SIGNUP;
import static com.example.logs.enums.UserRole.STAFF;
import static com.example.logs.enums.UserRole.VISITOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {
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
    @InjectMocks
    private StaffService staffService;


    private User visitor;
    private User staff;

    @BeforeEach
    void setUp() {
        visitor = new User();
        visitor.setRole(VISITOR);
        staff = new User();
        staff.setPassword("password");
        staff.setRole(STAFF);
    }

    @Test
    void addNewStaff() {
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        when(modelMapper.map(signupRequestDto, User.class)).thenReturn(staff);
        String value = staffService.addNewStaff(signupRequestDto);
        assertThat(value)
                .isNotNull()
                .isEqualTo(SIGNUP);
    }

    @Test
    void getAllStaff() {
        List<User> staffs = List.of(staff);
        when(userRepository.findUsersByRole(STAFF)).thenReturn(staffs);
        List<ServiceResponseDto> serviceResponseDtos = staffService.getAllStaff();
        assertThat(serviceResponseDtos).isNotNull();
        assertThat(serviceResponseDtos.size()).isEqualTo(staffs.size());
    }

    @Test
    void getStaff() {
        long visitorId = 1;
        when(userRepository.findById(visitorId)).thenReturn(Optional.of(staff));
        ServiceResponseDto responseDto = new ServiceResponseDto();
        when(modelMapper.map(staff, ServiceResponseDto.class)).thenReturn(responseDto);
        ServiceResponseDto serviceResponseDto = staffService.getStaff(visitorId);
        assertThat(serviceResponseDto).isNotNull();
    }
}