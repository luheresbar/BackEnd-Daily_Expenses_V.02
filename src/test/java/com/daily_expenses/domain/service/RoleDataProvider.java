package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.model.Role;

import java.util.List;
import java.util.Set;

public class RoleDataProvider {

    public static List<Role> roleListMock() {
        List<Permission> permissions = PermissionDataProvider.permissionListMock();
        Permission createPermission = permissions.get(0);
        Permission readPermission = permissions.get(1);
        Permission updatePermission = permissions.get(2);
        Permission deletePermission = permissions.get(3);
        Permission refactorPermission = permissions.get(4);

        Role adminRole = new Role(1L, "ADMIN", Set.of(createPermission, readPermission, updatePermission, deletePermission));
        Role userRole = new Role(2L, "USER", Set.of(createPermission, readPermission));
        Role invitedRole = new Role(3L, "INVITED", Set.of(readPermission));
        Role developerRole = new Role(4L, "DEVELOPER", Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission));

        return List.of(adminRole, userRole, invitedRole, developerRole);
    }

    public static Role newRoleMock() {
        Permission readPermission = PermissionDataProvider.permissionListMock().get(1);
        return new Role(2L, "USER", Set.of(readPermission));
    }
}
