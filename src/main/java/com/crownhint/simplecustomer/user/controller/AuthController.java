package com.crownhint.simplecustomer.user.controller;

import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController (UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CreateUserDto createCustomerRequest) {
        log.info("CreateUserRequest -> {}", createCustomerRequest);
        UserDto responseBody = userService.createUser(createCustomerRequest);
        ApiResponse body = ApiResponse.builder()
                .status("Success")
                .message("User created successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        log.info("Successfully created user. Exiting controller method save Customer.");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}