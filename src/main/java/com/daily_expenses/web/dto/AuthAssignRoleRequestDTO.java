package com.daily_expenses.web.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Validated
public record AuthAssignRoleRequestDTO(

        @Size(max = 3, message = "The user cannot have more than 3 roles")
        List<String> roleListName) {

        public AuthAssignRoleRequestDTO {
                roleListName = roleListName.stream()
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());
        }
}
