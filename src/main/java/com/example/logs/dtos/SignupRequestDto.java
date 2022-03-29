package com.example.logs.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "Enter your name")
    private String name;

    @Email(message = "Must be a valid email!")
    private String  email;

    @NotBlank(message = "Enter your phone number")
    private String phoneNumber;

    @NotBlank(message = "Enter your address")
    private String address;

    @NotBlank(message = "Enter your username")
    private String userName;

    @NotBlank(message = "Enter your password")
    private String password;

}
