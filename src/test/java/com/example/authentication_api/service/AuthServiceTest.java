package com.example.authentication_api.service;

import com.example.authentication_api.exception.UserAlreadyExistsException;
import com.example.authentication_api.repository.UserRepository;
import com.example.authentication_api.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(UserCreator.createValidUser());
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
}