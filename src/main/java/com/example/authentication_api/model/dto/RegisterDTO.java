package com.example.authentication_api.model.dto;

import com.example.authentication_api.model.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterDTO(@Email String email,
                          @NotBlank @NotEmpty @NotNull String password,
                          @NotEmpty @NotNull List<UserRole> roles) {
}
