package com.daily_expenses.infrastructure.persistence.repository.crud;

import com.daily_expenses.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface IRoleCrudRepository extends CrudRepository<RoleEntity, Long> {
}
