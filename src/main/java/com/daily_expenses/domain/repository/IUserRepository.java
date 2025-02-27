package com.daily_expenses.domain.repository;

import com.daily_expenses.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    User save(User user);

}
