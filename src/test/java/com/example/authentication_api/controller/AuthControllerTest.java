package com.example.authentication_api.controller;

import com.example.authentication_api.model.dto.LoginResponseDTO;
import com.example.authentication_api.service.AuthService;
import com.example.authentication_api.util.TokenCreator;
import com.example.authentication_api.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @InjectMocks
    AuthController authController;
    @Mock
    AuthService authService;

    void setUp() {
        BDDMockito.doNothing().when(authService).register(ArgumentMatchers.any());
        BDDMockito.when(authService.login(ArgumentMatchers.any())).thenReturn(TokenCreator.createTokenUserRole());
    }

    @Test
    void register_Return201_WhenSuccessful() {
        ResponseEntity<Void> response = authController.register(UserCreator.createValidRegisterDTO());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

    }

    @Test
    void login_Return200_WhenSuccessful() {
        ResponseEntity<LoginResponseDTO> response = authController.login(UserCreator.createValidLoginDTO());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

    }
}