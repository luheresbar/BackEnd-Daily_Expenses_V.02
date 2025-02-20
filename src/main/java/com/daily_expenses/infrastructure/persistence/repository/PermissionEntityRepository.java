package com.daily_expenses.infrastructure.persistence.repository;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.repository.IPermissionRepository;
import com.daily_expenses.infrastructure.persistence.entity.PermissionEntity;
import com.daily_expenses.infrastructure.persistence.repository.crud.IPermissionCrudRepository;
import com.daily_expenses.util.mapper.IPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PermissionEntityRepository implements IPermissionRepository {

    @Autowired
    private IPermissionCrudRepository permissionCrudRepository;
    @Autowired
    private IPermissionMapper permissionMapper;

    @Override
    public List<Permission> findAll() {
        List<PermissionEntity> permissionEntities = (List<PermissionEntity>) this.permissionCrudRepository.findAll();
        return this.permissionMapper.toPermissionList(permissionEntities);
    }
}
