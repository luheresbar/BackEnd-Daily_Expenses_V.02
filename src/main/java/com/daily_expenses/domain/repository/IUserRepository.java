package com.daily_expenses.domain.repository;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.infrastructure.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

}
