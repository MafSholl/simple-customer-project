package com.crownhint.simplecustomer.security.entrypoint.service;

import com.crownhint.simplecustomer.security.jwt.JwtService;
import com.crownhint.simplecustomer.security.entrypoint.dtos.AuthenticationDto;
import com.crownhint.simplecustomer.security.entrypoint.dtos.AuthenticationResponse;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.models.User;
import com.crownhint.simplecustomer.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserService userService,
                           JwtService jwtService,
                           ModelMapper modelMapper,
                           AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public AuthenticationResponse register(CreateUserDto registerRequest) {
        log.info("Register request: -> {}", registerRequest);
        UserDto registeredUser = userService.createUser(registerRequest);
        var jwtToken = jwtService.generateToken(modelMapper.map(registeredUser, User.class));
        return new AuthenticationResponse("Registration successful!", jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationDto authenticationRequest) {
        log.info("Login request user:{} -> {}", authenticationRequest.getEmail(), authenticationRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDto userDto = userService.findUser(authenticationRequest.getEmail());
        String jwtToken = jwtService.generateToken(modelMapper.map(userDto, User.class));
        return new AuthenticationResponse("Login successful", jwtToken);
    }
}
