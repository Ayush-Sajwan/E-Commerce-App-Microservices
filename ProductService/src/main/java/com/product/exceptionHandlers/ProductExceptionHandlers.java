package com.product.exceptionHandlers;

import com.product.exceptions.ProductNotFoundException;
import com.product.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandlers {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> productNotFoundException(ProductNotFoundException ex){

        return ResponseEntity.status(500).body(
                new ApiResponse<String>("Request Failed...",ex.getMsg())
        );
    }
}
