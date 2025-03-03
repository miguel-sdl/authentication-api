package com.example.authentication_api.model.dto;

import jakarta.validation.constraints.Email;

public record LoginDTO(@Email String email, String password) {
}
