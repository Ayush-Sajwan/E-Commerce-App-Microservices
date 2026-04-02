package com.order_service.exception_handlers;

import com.order_service.exception.InterMSCommException;
import com.order_service.exception.OrderProcessingException;
import com.order_service.response.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InterMSCommException.class)
    public ResponseEntity<ApiResponse<String>> handleInterMSCommException(InterMSCommException ex){

        return ResponseEntity.status(HttpStatusCode.valueOf(503))
                .body(new ApiResponse<>("FAILED",ex.getMsg()));
    }

    @ExceptionHandler(OrderProcessingException.class)
    public ResponseEntity<ApiResponse<String>> handleOrderProcessingException(OrderProcessingException ex){

        return ResponseEntity.status(HttpStatusCode.valueOf(503))
                .body(new ApiResponse<>("FAILED",ex.getMsg()));
    }
}
