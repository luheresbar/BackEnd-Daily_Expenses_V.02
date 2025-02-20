package com.daily_expenses.infrastructure.persistence.repository.crud;

import com.daily_expenses.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.data.repository.CrudRepository;

public interface IPermissionCrudRepository extends CrudRepository<PermissionEntity, Long> {
}
