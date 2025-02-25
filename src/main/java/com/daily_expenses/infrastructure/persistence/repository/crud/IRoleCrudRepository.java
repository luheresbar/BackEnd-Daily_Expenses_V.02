package com.daily_expenses.infrastructure.persistence.repository.crud;

import com.daily_expenses.infrastructure.persistence.entity.RoleEntity;
import com.daily_expenses.infrastructure.persistence.entity.RoleEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IRoleCrudRepository extends CrudRepository<RoleEntity, Long> {
    List<RoleEntity> findRoleEntitiesByRoleNameIn(List<String> roleNames);
    Optional<RoleEntity> findRoleById(Long id);

    Optional<RoleEntity> findByRoleName(RoleEnum roleName);

    Boolean existsByRoleName(RoleEnum roleEnum);
}