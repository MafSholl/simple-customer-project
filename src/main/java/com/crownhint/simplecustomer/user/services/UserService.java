package com.crownhint.simplecustomer.user.services;

import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.LoginDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(CreateUserDto createUserRequest);

    UserDto findUser(String email);

    List<UserDto> findAllUsers();

    UserDto login(LoginDto loginRequest);
}
