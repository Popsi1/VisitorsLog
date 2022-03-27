package com.example.logs.models;
import com.example.visitorslogs.dtos.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Staff extends BaseModel{

    @Column(name = "staff_name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email_address", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    public static Staff from(SignupRequestDto signupRequestDto) {
        Staff staff = new Staff();
        staff.setName(signupRequestDto.getName());
        staff.setEmail(signupRequestDto.getEmail());
        staff.setPhoneNumber(signupRequestDto.getPhoneNumber());
        staff.setAddress(signupRequestDto.getAddress());
        staff.setUserName(signupRequestDto.getUserName());
        staff.setPassword(signupRequestDto.getPassword());

        return staff;
    }
}
