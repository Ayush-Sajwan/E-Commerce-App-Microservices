package com.inventory_service.exception_handler;

import com.inventory_service.exception.ControllerRequestException;
import com.inventory_service.exception.InventoryProcessingException;
import com.inventory_service.response.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        String msg= ex.getBindingResult().
                getFieldErrors().stream().
                map(fieldError -> fieldError.getField()+" :"+fieldError.getDefaultMessage()+" ")
                .collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(new ApiResponse<>("Not a valid request",msg));
    }

    @ExceptionHandler(InventoryProcessingException.class)
    public ResponseEntity<ApiResponse<String>> handleInventoryProcessingException(InventoryProcessingException ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(new ApiResponse<>("Error while processing the request",ex.getMessage()));
    }

    @ExceptionHandler(ControllerRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleControllerProcessingException(ControllerRequestException ex){
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(new ApiResponse<>("Error while processing the request",ex.getMessage()));
    }
}
