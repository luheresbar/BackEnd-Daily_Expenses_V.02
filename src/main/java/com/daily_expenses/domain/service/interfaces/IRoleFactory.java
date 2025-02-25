package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.web.dto.RoleUpdateRequestDTO;

public interface IRoleFactory {

    Role createRole(RoleUpdateRequestDTO roleCreateRequestDTO);

}
