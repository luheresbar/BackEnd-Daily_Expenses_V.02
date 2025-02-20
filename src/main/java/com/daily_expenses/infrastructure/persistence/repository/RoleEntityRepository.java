package com.daily_expenses.infrastructure.persistence.repository;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.infrastructure.persistence.entity.RoleEntity;
import com.daily_expenses.infrastructure.persistence.repository.crud.IRoleCrudRepository;
import com.daily_expenses.util.mapper.IRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleEntityRepository implements IRoleRepository {

    @Autowired
    private IRoleCrudRepository roleCrudRepository;

    @Autowired
    private IRoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        List<RoleEntity> roles = (List<RoleEntity>) this.roleCrudRepository.findAll();
        return this.roleMapper.toRoles(roles);
    }
}
