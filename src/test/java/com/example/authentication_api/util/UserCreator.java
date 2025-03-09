package com.example.authentication_api.util;

import com.example.authentication_api.model.dto.LoginDTO;
import com.example.authentication_api.model.dto.RegisterDTO;
import com.example.authentication_api.model.entity.User;
import com.example.authentication_api.model.entity.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class UserCreator {
    public static User createUserToBeSaved() {
        return User.builder()
                .email("teste@gmail.com")
                .password(new BCryptPasswordEncoder().encode("password"))
                .roles(List.of(UserRole.ROLE_USER))
                .build();
    }

    public static User createAdminUserToBeSaved() {
        return User.builder()
                .email("admin@gmail.com")
                .password(new BCryptPasswordEncoder().encode("password"))
                .roles(List.of(UserRole.ROLE_ADMIN))
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

    public static User createValidAdminUser() {
        return User.builder()
                .id(1)
                .email("admin@gmail.com")
                .password("password")
                .roles(List.of(UserRole.ROLE_ADMIN))
                .build();
    }

    public static RegisterDTO createValidRegisterDTO() {
        return new RegisterDTO("teste@gmail.com", "password", List.of(UserRole.ROLE_USER));
    }

    public static RegisterDTO createInvalidRegisterDTO() {
        return new RegisterDTO("a", "", null);
    }

    public static LoginDTO createValidLoginDTO() {
        return new LoginDTO("teste@gmail.com", "password");
    }
}
