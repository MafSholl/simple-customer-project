package com.crownhint.simplecustomer.user.services;

import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;

import java.util.List;

public interface UserDao {
    UserDto createUser(CreateUserDto createUserDto);

    UserDto findUser(String email);

    List<UserDto> findAllUsers();
}
