package com.example.shopapplication.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForbiddenDeniedException {

    String message;

    public ForbiddenDeniedException () {
        message = "You have user permission and can be accessed to admin data";
    }
}
