package com.example.logs.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class SignupRequestDto {

    private String name;

    @Email(message = "Must be a valid email!")
    private String  email;

    private String phoneNumber;

    private String address;

    private String userName;

    private String password;

//    @NotBlank
//    @Size(min = 10)
//    private String password;
//    @NotBlank
//    private String userName;
//
//    @NotBlank
//    private String role;
//    @NotBlank
//    private String mainCurrency;

}
