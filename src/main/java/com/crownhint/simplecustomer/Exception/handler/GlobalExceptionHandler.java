package com.crownhint.simplecustomer.Exception.handler;

import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SimpleCustomerException.class)
    public ResponseEntity<?> conflictHandler(SimpleCustomerException ex) {
        String info = ExceptionMessage.makeMessage(ex);
        ApiResponse body = ApiResponse.builder()
                .status("Failure")
                .message(info)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        log.info("ExceptionMessage.makeMessage() : -> {}", info);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> conflictHandler(Exception ex) {
        ApiResponse body = ApiResponse.builder()
                .status("Something happened. Please try again.")
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_GATEWAY.value())
                .build();
        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }
}
