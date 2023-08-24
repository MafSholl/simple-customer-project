package com.crownhint.simplecustomer.user.controller.response;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse implements Serializable {
    private String status;
    private String message;
    private int statusCode;
    private Object data;
}
