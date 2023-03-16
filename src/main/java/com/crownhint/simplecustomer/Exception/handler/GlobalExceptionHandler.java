package com.crownhint.simplecustomer.Exception.handler;

import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import com.crownhint.simplecustomer.user.controller.response.ApiError;
import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> conflictHandler(SimpleCustomerException ex) {
        ApiResponse body = ApiResponse.builder()
                .status("Failure")
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(body, HttpStatusCode.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler
    public ResponseEntity<?> conflictHandler(Exception ex) {
        ApiResponse body = ApiResponse.builder()
                .status("something happened. Please try again.")
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_GATEWAY.value())
                .build();
        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }
}
