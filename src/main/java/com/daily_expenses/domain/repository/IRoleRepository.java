package com.daily_expenses.domain.repository;


import com.daily_expenses.domain.model.Role;

import java.util.List;

public interface IRoleRepository {

    List<Role> findAll();

}
