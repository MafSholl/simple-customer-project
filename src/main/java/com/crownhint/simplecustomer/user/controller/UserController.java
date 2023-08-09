package com.crownhint.simplecustomer.user.controller;

import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save-customer")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CreateUserDto createCustomerRequest) {
        log.info("CreateUserRequest -> {}", createCustomerRequest);
        UserDto responseBody = userService.createUser(createCustomerRequest);
        ApiResponse body = ApiResponse.builder()
                .status("Success")
                .message("Customer created successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        log.info("Successful created customer. Exiting controller method save(ustomer.");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/find-customer")
    public ResponseEntity<?> findCustomer(@RequestParam("email") String email) {
        log.info("Request param email is: -> {}", email);
        UserDto responseBody = userService.findUser(email);
        ApiResponse body = ApiResponse.builder()
                .status("Success")
                .message("Customer found successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        log.info("Successfully found customer. Exiting controller method findCustomer");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllCustomer() {
        log.info("Find all endpoint hit");
        List<UserDto> responseBody = userService.findAllUsers();
        ApiResponse body = ApiResponse.builder()
                .status("Success")
                .message("All customers found successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        log.info("Successfully found all customers. Exiting controller method findAllCustomer");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
