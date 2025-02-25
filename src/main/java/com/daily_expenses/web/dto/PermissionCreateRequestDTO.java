package com.daily_expenses.web.dto;

import jakarta.validation.constraints.NotBlank;

public record PermissionCreateRequestDTO(
        @NotBlank String permissionName
) {
    public PermissionCreateRequestDTO {
        permissionName = permissionName.toUpperCase();
    }
}
