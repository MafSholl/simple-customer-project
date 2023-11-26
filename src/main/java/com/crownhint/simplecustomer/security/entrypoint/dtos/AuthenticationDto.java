package com.crownhint.simplecustomer.security.entrypoint.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationDto {
    private String email;
    private String password;
    private String token;
}
