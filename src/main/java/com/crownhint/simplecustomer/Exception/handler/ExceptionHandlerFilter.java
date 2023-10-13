package com.crownhint.simplecustomer.Exception.handler;

import com.crownhint.simplecustomer.user.controller.response.ApiError;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        } catch (JwtException ex) {
            ex.printStackTrace();
            setErrorResponse(HttpStatus.BAD_REQUEST, request, response, ex);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, response, exception);
        }

    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ApiError apiError = new ApiError(status, ex);
        apiError.setPath(request.getServletPath());
        try {
            String JsonOutput = apiError.convertToJson();
            response.getWriter().write(JsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
