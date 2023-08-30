package com.crownhint.simplecustomer.auth.entrypoint.service;

import com.crownhint.simplecustomer.auth.jwt.JwtService;
import com.crownhint.simplecustomer.auth.entrypoint.dtos.AuthenticationDto;
import com.crownhint.simplecustomer.auth.entrypoint.dtos.AuthenticationResponse;
import com.crownhint.simplecustomer.auth.jwt.JwtServiceImpl;
import com.crownhint.simplecustomer.user.dtos.CreateUserDto;
import com.crownhint.simplecustomer.user.dtos.UserDto;
import com.crownhint.simplecustomer.user.models.User;
import com.crownhint.simplecustomer.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
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
        UserDto registeredUser = userService.createUser(registerRequest);
        var jwtToken = jwtService.generateToken(modelMapper.map(registeredUser, User.class));
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationDto authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        UserDto userDto = userService.findUser(authenticationRequest.getEmail());
        String jwtToken = jwtService.generateToken(modelMapper.map(userDto, User.class));
        return new AuthenticationResponse(jwtToken);
    }
}
