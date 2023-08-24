package com.crownhint.simplecustomer.user.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDto {
    private String username;
    private String password;
}
