package com.daily_expenses.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.stream.Collectors;

public record RoleUpdateRequestDTO(
        @NotBlank String roleName,
        @NotBlank List<String> permissionListName
) {

    public RoleUpdateRequestDTO {
        roleName = roleName.toUpperCase();
        permissionListName = permissionListName.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}