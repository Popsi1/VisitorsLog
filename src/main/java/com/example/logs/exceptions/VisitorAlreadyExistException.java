package com.example.logs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VisitorAlreadyExistException extends RuntimeException{
    public VisitorAlreadyExistException(String message) {
        super(message);}
}