package com.crownhint.simplecustomer.auth.entrypoint.service;

import com.crownhint.simplecustomer.auth.entrypoint.dtos.AuthenticationDto;
import com.crownhint.simplecustomer.auth.entrypoint.dtos.AuthenticationResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;

public interface AuthService {

    AuthenticationResponse register(CreateUserDto registerRequest);
    AuthenticationResponse authenticate(AuthenticationDto authenticationRequest);
}
