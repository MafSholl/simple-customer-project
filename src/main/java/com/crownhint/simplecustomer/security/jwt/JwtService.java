package com.crownhint.simplecustomer.security.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    public String generateToken(Map<String, Object> tokenClaims, UserDetails userDetails);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
