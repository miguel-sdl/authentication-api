package com.example.authentication_api.util;

import com.example.authentication_api.model.dto.LoginDTO;
import com.example.authentication_api.model.dto.RegisterDTO;
import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.model.entity.UserRole;

import java.util.List;

public class UserCreator {
    public static User createUserToBeSaved() {
        return User.builder()
                .email("teste@gmail.com")
                .password("password")
                .roles(List.of(UserRole.ROLE_USER))
                .build();
    }

    public static User createValidUser() {
        return User.builder()
                .id(1)
                .email("teste@gmail.com")
                .password("password")
                .roles(List.of(UserRole.ROLE_USER))
                .build();
    }

    public static RegisterDTO createValidRegisterDTO() {
        return new RegisterDTO("test@gmail.com", "password", List.of(UserRole.ROLE_USER));
    }

    public static LoginDTO createValidLoginDTO() {
        return new LoginDTO("test@gmail.com", "password");
    }
}
