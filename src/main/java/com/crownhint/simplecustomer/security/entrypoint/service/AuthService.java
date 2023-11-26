package com.crownhint.simplecustomer.security.entrypoint.service;

import com.crownhint.simplecustomer.security.entrypoint.dtos.AuthenticationDto;
import com.crownhint.simplecustomer.security.entrypoint.dtos.AuthenticationResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;

public interface AuthService {

    AuthenticationResponse register(CreateUserDto registerRequest);
    AuthenticationResponse authenticate(AuthenticationDto authenticationRequest);
}
