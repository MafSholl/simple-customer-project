package com.crownhint.simplecustomer.user.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateUserDto {
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    @NonNull private String role;
}
