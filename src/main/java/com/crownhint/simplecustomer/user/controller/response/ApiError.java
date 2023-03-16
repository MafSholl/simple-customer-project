package com.crownhint.simplecustomer.user.controller.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiError {
    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }
    @Builder
    public ApiError(HttpServletRequest request) {
        this();
        this.path = request.getServletPath();
    }
}
