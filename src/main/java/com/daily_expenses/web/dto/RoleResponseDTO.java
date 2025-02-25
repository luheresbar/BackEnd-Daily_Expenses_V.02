package com.daily_expenses.web.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"roleName", "message", "permissions"})
public record RoleResponseDTO(
        String roleName,
        String message,
        List<String> permissions
) {
}
