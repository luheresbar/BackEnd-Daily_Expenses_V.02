package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.repository.IPermissionRepository;
import com.daily_expenses.domain.service.interfaces.IPermissionService;
import com.daily_expenses.web.dto.PermissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return this.permissionRepository.findAll();
    }

    @Override
    public PermissionDTO create(String permissionName) {
        Permission newPermission = new Permission();
        newPermission.setName(permissionName);
        Permission permission = this.permissionRepository.save(newPermission);
        return new PermissionDTO(permission.getName(), Optional.of("Permission created successfully"));
    }
}
