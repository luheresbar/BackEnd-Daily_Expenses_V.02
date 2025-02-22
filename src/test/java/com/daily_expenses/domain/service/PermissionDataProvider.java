package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;

import java.util.List;

public class PermissionDataProvider {

    public static List<Permission> permissionListMock() {
        return List.of(
                new Permission(1L, "CREATE"),
                new Permission(2L, "READ"),
                new Permission(3L, "UPDATE"),
                new Permission(4L, "DELETE"),
                new Permission(5L, "REFACTOR")
        );
    }

    public static Permission newPermissionMock() {
        return new Permission(1L, "CREATE");
    }
}
