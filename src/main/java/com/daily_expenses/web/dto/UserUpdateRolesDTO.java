package com.daily_expenses.web.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonPropertyOrder({"email", "roles", "message"})
public record UserUpdateRolesDTO(
        @NotBlank String email,
        List<@NotBlank String> roles,
        Optional<String> message

) {
    public UserUpdateRolesDTO {
        email = email.toLowerCase();
        roles = roles.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

}
