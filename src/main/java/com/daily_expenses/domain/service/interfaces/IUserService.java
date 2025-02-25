package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import com.daily_expenses.web.dto.UserUpdateRolesDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    AuthResponseDTO createUser(AuthCreateUserRequestDTO createUserRequest);

    UserUpdateRolesDTO updateUserRoles(UserUpdateRolesDTO updateUserRolesDTO);
}
