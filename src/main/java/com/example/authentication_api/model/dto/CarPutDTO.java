package com.example.authentication_api.model.dto;

import jakarta.validation.constraints.*;

public record CarPutDTO(int id,
                        @NotBlank @NotEmpty @NotNull String name,
                        @Positive double price,
                        @Min(1900) int year) {
}
