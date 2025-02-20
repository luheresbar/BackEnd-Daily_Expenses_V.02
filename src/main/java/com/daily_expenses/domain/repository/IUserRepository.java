package com.daily_expenses.domain.repository;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public interface IUserRepository {

    List<User> findAll();

}
