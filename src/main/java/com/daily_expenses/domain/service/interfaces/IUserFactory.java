package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;

public interface IUserFactory {

    User createUser(AuthCreateUserRequestDTO createUserRequest);
}
