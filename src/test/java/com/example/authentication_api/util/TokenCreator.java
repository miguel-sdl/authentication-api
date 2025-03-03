package com.example.authentication_api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authentication_api.security.TokenService;

import java.time.Instant;

public class TokenCreator {
    private static final TokenService tokenService;

    static {
        tokenService = new TokenService("test");
    }

    public static String createValidToken() {
        return tokenService.generateToken(UserCreator.createValidUser());
    }

    public static String createExpiredToken() {
        return JWT.create()
                .withSubject("teste")
                .withExpiresAt(Instant.now())
                .sign(Algorithm.HMAC256("secret"));

    }
}
