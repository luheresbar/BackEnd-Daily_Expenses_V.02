package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.web.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<Role> findAll();

    Optional<Role> findByRoleName(String roleName);

    RoleDTO save(RoleDTO roleDTO);
}
