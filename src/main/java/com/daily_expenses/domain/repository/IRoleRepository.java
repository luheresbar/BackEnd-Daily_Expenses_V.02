package com.daily_expenses.domain.repository;


import com.daily_expenses.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository {

    List<Role> findAll();
    Optional<Role> findById(Long roleId);
    Optional<Role> findByRoleName(String roleName);
    Boolean existByRoleName(String roleName);
    List<Role> findRoleEntitiesByRoleNameIn(List<String> roleNames);
    Role save(Role role);


}
