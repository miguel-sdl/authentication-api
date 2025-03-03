package com.example.authentication_api.service;

import com.example.authentication_api.exception.UserAlreadyExistsException;
import com.example.authentication_api.repository.UserRepository;
import com.example.authentication_api.security.TokenService;
import com.example.authentication_api.util.TokenCreator;
import com.example.authentication_api.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @InjectMocks
    AuthService authService;
    @Mock
    UserRepository userRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    TokenService tokenService;
    @Mock
    AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(UserCreator.createValidUser());
        BDDMockito.when(tokenService.generateToken(ArgumentMatchers.any())).thenReturn(TokenCreator.createValidToken());
        BDDMockito.when(authenticationManager.authenticate(ArgumentMatchers.any())).thenReturn(new UsernamePasswordAuthenticationToken(UserCreator.createValidUser(), ""));
    }

    @Test
    void register_ThrowsUserAlreadyExistsException_WhenUserAlreadyExist() {
        Assertions.assertThatThrownBy(() -> authService.register(UserCreator.createValidRegisterDTO()))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void register_RegisterUser_WhenSuccessful() {
        BDDMockito.when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(null);

        Assertions.assertThatCode(() -> authService.register(UserCreator.createValidRegisterDTO())).doesNotThrowAnyException();
    }

    @Test
    void login_ReturnsJWTToken_WhenSuccessful() {
        String token = authService.login(UserCreator.createValidLoginDTO());
        String[] split = token.split("\\.");

        Assertions.assertThat(token).isNotNull().isNotEmpty();
        Assertions.assertThat(split.length).isEqualTo(3);
    }
}