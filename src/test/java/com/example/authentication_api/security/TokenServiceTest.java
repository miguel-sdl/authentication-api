package com.example.authentication_api.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.util.TokenCreator;
import com.example.authentication_api.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class TokenServiceTest {
    TokenService tokenService = new TokenService("secret");

    @Test
    void generateToken_GeneratesToken_WhenSuccessful() {
        User user = UserCreator.createValidUser();

        String token = tokenService.generateToken(user);
        String[] split = token.split("\\.");


        Assertions.assertThat(token).isNotNull().isNotEmpty();
        Assertions.assertThat(split.length).isEqualTo(3);
    }

    @Test
    void validateToken_ReturnUsername_WhenSuccessful() {
        User user = UserCreator.createValidUser();

        String token = tokenService.generateToken(user);
        String username = tokenService.validateToken(token);

        Assertions.assertThat(username).isNotNull().isNotEmpty();
        Assertions.assertThat(username).isEqualTo(user.getUsername());
    }

    @Test
    void validateToken_ThrowsJWTVerificationException_WhenTokenIsNotValid() {
        String token = tokenService.generateToken(UserCreator.createValidUser());

        String invalidToken = token.replace('e', 'a');

        Assertions.assertThatThrownBy(() -> tokenService.validateToken(invalidToken))
                .isInstanceOf(JWTVerificationException.class);


    }

    @Test
    void validateToken_ThrowsTokenExpiredException_WhenTokenIsExpired() {

        Assertions.assertThatThrownBy(() -> tokenService.validateToken(TokenCreator.createExpiredToken()))
                .isInstanceOf(TokenExpiredException.class);


    }

}