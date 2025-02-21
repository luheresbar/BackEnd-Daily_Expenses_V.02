package com.daily_expenses.domain.repository;


import com.daily_expenses.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository {

    List<Role> findAll();
    Optional<Role> findById(Long roleId);
    List<Role> findRoleEntitiesByRoleEnumIn(List<String> roleNames);


}
