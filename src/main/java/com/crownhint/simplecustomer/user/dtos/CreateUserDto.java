package com.crownhint.simplecustomer.user.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
