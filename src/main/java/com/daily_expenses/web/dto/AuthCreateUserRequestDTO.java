package com.daily_expenses.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequestDTO(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password,
        @Valid AuthCreateRoleRequestDTO roleRequest) {
}
