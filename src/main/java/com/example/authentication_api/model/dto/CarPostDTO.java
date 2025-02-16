package com.example.authentication_api.model.dto;

import jakarta.validation.constraints.*;


public record CarPostDTO(@NotBlank @NotNull @NotEmpty String name,
                         @Positive double price,
                         @Min(1900) int year) {
}
