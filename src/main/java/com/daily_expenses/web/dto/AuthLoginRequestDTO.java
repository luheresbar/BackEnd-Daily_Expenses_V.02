package com.daily_expenses.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(@NotBlank String email,
                               @NotBlank String password) {
}
