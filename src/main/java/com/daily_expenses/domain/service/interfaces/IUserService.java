package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findById(Long id);
}
