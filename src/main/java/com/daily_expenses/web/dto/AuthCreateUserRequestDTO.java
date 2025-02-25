package com.daily_expenses.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

public record AuthCreateUserRequestDTO(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password,
        @Size(max = 3, message = "The user cannot have more than 3 roles")
        List<@NotBlank  String> roleListName) {

    public AuthCreateUserRequestDTO {
        roleListName = roleListName.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        password = password.toLowerCase();
    }
}
