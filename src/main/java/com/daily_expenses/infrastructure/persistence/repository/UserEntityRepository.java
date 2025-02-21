package com.daily_expenses.infrastructure.persistence.repository;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.infrastructure.persistence.entity.RoleEntity;
import com.daily_expenses.infrastructure.persistence.entity.UserEntity;
import com.daily_expenses.infrastructure.persistence.repository.crud.IRoleCrudRepository;
import com.daily_expenses.infrastructure.persistence.repository.crud.IUserCrudRepository;
import com.daily_expenses.infrastructure.persistence.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserEntityRepository implements IUserRepository {

    @Autowired
    private IUserCrudRepository userCrudRepository;
    @Autowired
    private IRoleCrudRepository roleCrudRepository;
    @Autowired
    private IUserMapper userMapper;

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = (List<UserEntity>) this.userCrudRepository.findAll();
        return this.userMapper.toUsers(userEntities);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntity = this.userCrudRepository.findById(id);
        return userEntity.map(entity -> this.userMapper.toUser(entity));

    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity = this.userCrudRepository.findUserEntityByEmail(email);
        return userEntity.map(entity -> this.userMapper.toUser(entity));
    }

    @Override
    @Transactional
    public User save(User user) {
        // Convert domain user to persistence user entity
        UserEntity userEntity = this.userMapper.toUserEntity(user);
        System.out.println("userEntity antes de managedRoles: " + userEntity);

        // Ensure roles are managed entities
        Set<RoleEntity> managedRoles = userEntity.getRoles().stream()
                .map(role -> roleCrudRepository.findRoleById(role.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role.getId())))
                .collect(Collectors.toSet());

        userEntity.setRoles(managedRoles);
        System.out.println("userEntity despues de managedRoles: " + userEntity);


        // Save the user entity
        UserEntity savedUserEntity = this.userCrudRepository.save(userEntity);

        // Convert back to domain user and return
        return this.userMapper.toUser(savedUserEntity);
    }

}
