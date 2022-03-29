package com.example.logs.services;

import com.example.logs.dtos.SignupRequestDto;
import com.example.logs.exceptions.UsernameAlreadyExistException;
import com.example.logs.models.User;
import com.example.logs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void checkIfUsernameOrEmailAlreadyExist(SignupRequestDto signupRequestDto) {
        Optional<User> optionalUser = userRepository.findUserByEmailOrUsername(signupRequestDto.getEmail(), signupRequestDto.getPassword());
        if (optionalUser.isPresent())
            throw new UsernameAlreadyExistException("Email/Username already exist");
    }
}
