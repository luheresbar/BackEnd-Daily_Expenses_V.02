package com.daily_expenses.infrastructure.persistence.mapper;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRoleMapper {

    Role toRole(RoleEntity roleEntity);

    List<Role> toRoles(List<RoleEntity> roleEntities);

    RoleEntity toRoleEntity(Role role);

}
