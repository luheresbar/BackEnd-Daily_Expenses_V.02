package com.daily_expenses.domain.repository;


import com.daily_expenses.domain.model.Permission;

import java.util.List;

public interface IPermissionRepository {

    List<Permission> findAll();
    Permission save(Permission permissionName);
}
