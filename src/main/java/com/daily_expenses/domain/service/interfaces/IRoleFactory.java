package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.web.dto.RoleDTO;

public interface IRoleFactory {

    Role createRole(RoleDTO roleCreateRequestDTO);

}
