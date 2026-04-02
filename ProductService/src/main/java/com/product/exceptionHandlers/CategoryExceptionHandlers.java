package com.product.exceptionHandlers;

import com.product.exceptions.CategoryProcessingError;
import com.product.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class CategoryExceptionHandlers {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleCategoryValidationFailure(MethodArgumentNotValidException ex){

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage()+"\n")
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(400).body(
                new ApiResponse<String>("Not a Valid Request",errors)
        );
    }

    @ExceptionHandler(CategoryProcessingError.class)
    public ResponseEntity<ApiResponse<String>> handleCategoryProcessingError(CategoryProcessingError ex){

        return ResponseEntity.status(400).body(
                new ApiResponse<String>("Error Occurred While processing Request",ex.getMsg())
        );
    }
}
