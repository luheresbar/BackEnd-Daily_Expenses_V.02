package com.daily_expenses.infrastructure.persistence.repository.crud;

import com.daily_expenses.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IRoleCrudRepository extends CrudRepository<RoleEntity, Long> {
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
    Optional<RoleEntity> findRoleById(Long id);
}