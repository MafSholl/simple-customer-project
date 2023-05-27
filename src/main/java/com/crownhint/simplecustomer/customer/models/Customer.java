package com.crownhint.simplecustomer.customer.models;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    private String name;
    private Long id;
}
