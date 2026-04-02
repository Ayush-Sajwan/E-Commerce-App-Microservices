package com.payment_service.exception_handlers;

import com.payment_service.exception.PaymentProcessingException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentExceptionHandler {

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<String> handlePaymentProcessingException(PaymentProcessingException exception){
        return ResponseEntity.status(HttpStatusCode.valueOf(402))
                .body(exception.getMsg());
    }
}
