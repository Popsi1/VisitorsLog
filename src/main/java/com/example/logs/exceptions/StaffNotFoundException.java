package com.example.logs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StaffNotFoundException extends RuntimeException{
    public StaffNotFoundException(String message) {
        super(message);}
}