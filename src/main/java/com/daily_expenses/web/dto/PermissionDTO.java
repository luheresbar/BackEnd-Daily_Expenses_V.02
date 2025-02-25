package com.daily_expenses.web.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Optional;

public record PermissionDTO(
        @NotBlank String permissionName,
        Optional<String> message
) {
    public PermissionDTO {
        permissionName = permissionName.toUpperCase();
    }
}