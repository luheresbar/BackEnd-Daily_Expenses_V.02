package com.daily_expenses.util.mapper;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.infrastructure.persistence.entity.PermissionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {

    Permission topermission(PermissionEntity permissionEntity);

    List<Permission> toPermissionList(List<PermissionEntity> permissionEntityList);

    PermissionEntity toPermissionEntity(Permission permission);
}
