package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.web.dto.PermissionDTO;

import java.util.List;

public interface IPermissionService {

    List<Permission> findAll();

    PermissionDTO create(String permissionName);
}
