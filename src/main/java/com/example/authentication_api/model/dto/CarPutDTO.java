package com.example.authentication_api.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CarPutDTO(int id,
                        @NotBlank String name,
                        @Positive double price,
                        @Min(1900) int year) {
}
