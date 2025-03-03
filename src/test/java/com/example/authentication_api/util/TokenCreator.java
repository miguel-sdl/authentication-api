package com.example.authentication_api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;

public class TokenCreator {
    public static String createExpiredToken() {
        return JWT.create()
                .withSubject("teste")
                .withExpiresAt(Instant.now())
                .sign(Algorithm.HMAC256("secret"));

    }
}
