package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.repository.IPermissionRepository;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.domain.service.interfaces.IRoleFactory;
import com.daily_expenses.web.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleFactory implements IRoleFactory {

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPermissionRepository permissionRepository;


    @Override
    public Role createRole(RoleDTO roleDTO) {
        String roleName = roleDTO.roleName();
        List<String> permissionsRequest = roleDTO.permissions().stream()
                .map(String::toUpperCase)
                .toList();

        // Fetch all valid permissions from the database
        List<Permission> allPermissions = this.permissionRepository.findAll();
        Set<String> validPermissions = allPermissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        // Check if any requested role does not exist
        for (String permissionName : permissionsRequest) {
            if (!validPermissions.contains(permissionName)) {
                throw new IllegalArgumentException("permission not found: " + permissionName);
            }
        }

        // Filter the permissions that match the requested roles
        Set<Permission> permissionList = allPermissions.stream()
                .filter(role -> permissionsRequest.contains(role.getName()))
                .collect(Collectors.toSet());

        return Role.builder()
                .roleName(roleName)
                .permissionList(permissionList).build();
    }
}
