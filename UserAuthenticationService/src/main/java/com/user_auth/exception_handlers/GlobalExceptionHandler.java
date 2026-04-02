package com.user_auth.exception_handlers;

import com.user_auth.exceptions.EmailAlreadyExistsException;
import com.user_auth.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Map<String,String>>> handleNotValidRequestException(MethodArgumentNotValidException exception){

        Map<String,String> errors=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->{
            errors.put(error.getField(),error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ApiResponse<Map<String,String>>("Not a Valid Request",errors));

    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    ResponseEntity<ApiResponse<Map<String,String>>> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception){

        Map<String,String> errors=new HashMap<>();

        errors.put("Email",exception.getErrorMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ApiResponse<Map<String,String>>("Not a Valid Request",errors));
    }
}
