package com.crownhint.simplecustomer.customer.services;

import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;

public interface CustomerService {

    UserDto createUser(CreateUserDto createUserDto);
}
