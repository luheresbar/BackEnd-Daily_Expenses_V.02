package com.daily_expenses.infrastructure.persistence.repository;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.infrastructure.persistence.entity.PermissionEntity;
import com.daily_expenses.infrastructure.persistence.entity.RoleEntity;
import com.daily_expenses.infrastructure.persistence.entity.RoleEnum;
import com.daily_expenses.infrastructure.persistence.repository.crud.IPermissionCrudRepository;
import com.daily_expenses.infrastructure.persistence.repository.crud.IRoleCrudRepository;
import com.daily_expenses.infrastructure.persistence.mapper.IRoleMapper;
import com.daily_expenses.util.RoleEnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleEntityRepository implements IRoleRepository {

    @Autowired
    private IRoleCrudRepository roleCrudRepository;
    @Autowired
    private IPermissionCrudRepository permissionCrudRepository;

    @Autowired
    private IRoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        List<RoleEntity> roles = (List<RoleEntity>) this.roleCrudRepository.findAll();
        return this.roleMapper.toRoles(roles);
    }

    @Override
    public Optional<Role> findById(Long roleId) {
        Optional<RoleEntity> userEntity = this.roleCrudRepository.findById(roleId);
        return userEntity.map(entity -> this.roleMapper.toRole(entity));
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        RoleEnum roleEnum = RoleEnumUtil.convertStringToRoleEnum(roleName);
        Optional<RoleEntity> userEntity = this.roleCrudRepository.findByRoleName(roleEnum);
        return userEntity.map(entity -> this.roleMapper.toRole(entity));
    }

    @Override
    public Boolean existByRoleName(String roleName) {
        RoleEnum roleEnum = RoleEnum.valueOf(roleName);
        return this.roleCrudRepository.existsByRoleName(roleEnum);
    }

    @Override
    public List<Role> findRoleEntitiesByRoleNameIn(List<String> roleNames) {
        List<RoleEntity> roles = this.roleCrudRepository.findRoleEntitiesByRoleNameIn(roleNames);
        return this.roleMapper.toRoles(roles);
    }

    @Override
    public Role save(Role role) {
        RoleEntity roleEntity = this.roleMapper.toRoleEntity(role);

        // Ensure permissions are managed entities
        Set<PermissionEntity> managedPermissions = roleEntity.getPermissionList().stream()
                .map(permission -> permissionCrudRepository.findById(permission.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permission.getId())))
                .collect(Collectors.toSet());

        roleEntity.setPermissionList(managedPermissions);

        RoleEntity savedRoleEntity = this.roleCrudRepository.save(roleEntity);
        // Convert back to domain role and return
        return this.roleMapper.toRole(savedRoleEntity);
    }


}
