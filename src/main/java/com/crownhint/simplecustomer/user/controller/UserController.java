package com.crownhint.simplecustomer.user.controller;

import com.crownhint.simplecustomer.user.controller.response.ApiResponse;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.services.UserService;
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


    @GetMapping("/get-customer")
    public ResponseEntity<?> findCustomer(@RequestParam("email") String email) {
        log.info("Request param email is: -> {}", email);
        UserDto responseBody = userService.findUser(email);
        ApiResponse body = ApiResponse.builder()
                .status("Success")
                .message("User found successfully")
                .data(responseBody)
                .statusCode(HttpStatus.OK.value())
                .build();
        log.info("Successfully found user. Exiting controller method findCustomer");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping
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
