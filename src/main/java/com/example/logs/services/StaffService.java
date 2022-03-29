package com.example.logs.services;

import com.example.logs.constant.Constants;
import com.example.logs.dtos.ServiceResponseDto;
import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.exceptions.StaffNotFoundException;
import com.example.logs.models.User;
import com.example.logs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.logs.enums.UserRole.STAFF;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public String addNewStaff(SignupRequestDto signupRequestDto) {
        userService.checkIfUsernameOrEmailAlreadyExist(signupRequestDto);
        User newStaff = modelMapper.map(signupRequestDto, User.class);
        newStaff.setPassword(passwordEncoder.encode(newStaff.getPassword()));
        newStaff.setRole(STAFF);
        userRepository.save(newStaff);

        return Constants.SIGNUP;
    }

    public List<ServiceResponseDto> getAllStaff(){
        List<User> listOfStaffs = userRepository.findUsersByRole(STAFF);
        List<ServiceResponseDto> listOfServiceResponseDto = new ArrayList<>();
        for (User staff : listOfStaffs) {
            ServiceResponseDto responseDto = modelMapper.map(staff, ServiceResponseDto.class);
            listOfServiceResponseDto.add(responseDto);
        }
        return listOfServiceResponseDto;
    }

    public ServiceResponseDto getStaff(Long staffId){
        User staff = userRepository.findById(staffId).orElseThrow(() -> new StaffNotFoundException("staff not found"));

        return modelMapper.map(staff, ServiceResponseDto.class);
    }
}
