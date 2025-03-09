package com.example.authentication_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authentication_api.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
public class TokenService {
    private final Algorithm ALGORITHM;


    public TokenService(@Value("${jwt.secret}") String secret) {
        this.ALGORITHM = Algorithm.HMAC256(secret);
    }

    public String generateToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(getExpiration())
                .sign(ALGORITHM);
    }

    public String validateToken(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant getExpiration() {
        return Instant.now().plus(1, ChronoUnit.HOURS);
    }
}
