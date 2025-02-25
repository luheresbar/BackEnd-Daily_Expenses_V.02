package com.daily_expenses.web.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonPropertyOrder({"roleName", "message", "permissions"})
public record RoleDTO(
        @NotBlank String roleName,
        Optional<String> message,
        @NotBlank List<String> permissions
) {
    public RoleDTO {
        roleName = roleName.toUpperCase();
        permissions = permissions.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}