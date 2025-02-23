package com.example.authentication_api.model.dto;

import com.example.authentication_api.model.entity.UserRole;
import jakarta.validation.constraints.Email;

import java.util.List;

public record RegisterDTO(@Email String email, String password, List<UserRole> roles) {
}
