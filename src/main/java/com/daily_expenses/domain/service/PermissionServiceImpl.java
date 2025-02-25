package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.repository.IPermissionRepository;
import com.daily_expenses.domain.service.interfaces.IPermissionService;
import com.daily_expenses.web.dto.PermissionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return this.permissionRepository.findAll();
    }

    @Override
    public PermissionResponseDTO create(String permissionName) {
        Permission newPermission = new Permission();
        newPermission.setName(permissionName);
        Permission permission = this.permissionRepository.save(newPermission);
        return new PermissionResponseDTO(permission.getName(), "Permission created successfully");
    }
}
