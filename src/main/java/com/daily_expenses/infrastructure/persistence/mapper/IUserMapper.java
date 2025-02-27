package com.daily_expenses.infrastructure.persistence.mapper;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    User toUser(UserEntity userEntity);

    List<User> toUsers(List<UserEntity> userEntities);

    UserEntity toUserEntity(User user);

}

