package com.example.shopapplication.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice //default this annotation exists in all controller but we can do it more specific
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleMobileIdException(MobilePhoneIdException ex, WebRequest request){
        MobilePhoneIdExceptionResponse exceptionResponse = new MobilePhoneIdExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidToken(InvalidTokenException ex, WebRequest request){
        System.out.println("Hello from handle exception" + ex.getMessage());
        InvalidTokenResponse invalidTokenResponse = new InvalidTokenResponse(ex.getMessage());
        return new ResponseEntity(invalidTokenResponse, HttpStatus.BAD_REQUEST);
    }
}
