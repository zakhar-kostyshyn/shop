package com.example.shopapplication.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MobilePhoneIdException extends RuntimeException {

    public MobilePhoneIdException(String message) {
        super(message);
    }
}
