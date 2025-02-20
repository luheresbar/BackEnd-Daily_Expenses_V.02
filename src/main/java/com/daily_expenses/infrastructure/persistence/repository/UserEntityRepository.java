package com.daily_expenses.infrastructure.persistence.repository;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.infrastructure.persistence.entity.UserEntity;
import com.daily_expenses.infrastructure.persistence.repository.crud.IUserCrudRepository;
import com.daily_expenses.util.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEntityRepository implements IUserRepository {

    @Autowired
    private IUserCrudRepository userCrudRepository;
    @Autowired
    private IUserMapper UserMapper;

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = (List<UserEntity>) this.userCrudRepository.findAll();
        return UserMapper.toUsers(userEntities);
    }
}
