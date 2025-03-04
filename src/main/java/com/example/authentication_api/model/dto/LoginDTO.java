package com.example.authentication_api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(@Email String email,
                       @NotBlank @NotEmpty @NotNull String password) {
}
