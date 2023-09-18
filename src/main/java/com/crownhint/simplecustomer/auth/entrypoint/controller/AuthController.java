package com.crownhint.simplecustomer.auth.entrypoint.controller;

import com.crownhint.simplecustomer.auth.entrypoint.dtos.AuthenticationDto;
import com.crownhint.simplecustomer.auth.entrypoint.dtos.AuthenticationResponse;
import com.crownhint.simplecustomer.auth.entrypoint.service.AuthService;
import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.LoginDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CreateUserDto createCustomerRequest) {
        log.info("CreateUserRequest -> {}", createCustomerRequest);
        AuthenticationResponse responseBody = authService.register(createCustomerRequest);
        ApiResponse body = ApiResponse.builder()
                .status("Success")
                .message("User created successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        log.info("Successfully created user. Exiting controller method api/v1/auth/signup");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto loginRequest) {
        log.info("[{}] Login request for user -> {}", LocalDateTime.now(), loginRequest.getEmail());
        AuthenticationResponse authResponse = authService.authenticate(loginRequest);
        log.info("Login successful for user -> {}", loginRequest.getEmail());
        ApiResponse body = ApiResponse.builder()
                .status("Success")
                .message("Login successful")
                .data(authResponse)
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

}
