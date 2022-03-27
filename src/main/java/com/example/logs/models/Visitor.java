package com.example.logs.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Visitor extends BaseModel{

    @Column(name = "visitor_name", nullable = false)
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

    @ManyToOne
    @JoinColumn
    private Staff staff;

    public static Visitor from(SignupRequestDto signupRequestDto) {
        Visitor visitor = new Visitor();
        visitor.setName(signupRequestDto.getName());
        visitor.setEmail(signupRequestDto.getEmail());
        visitor.setPhoneNumber(signupRequestDto.getPhoneNumber());
        visitor.setAddress(signupRequestDto.getAddress());
        visitor.setUserName(signupRequestDto.getUserName());
        visitor.setPassword(signupRequestDto.getPassword());

        return visitor;
    }
}
