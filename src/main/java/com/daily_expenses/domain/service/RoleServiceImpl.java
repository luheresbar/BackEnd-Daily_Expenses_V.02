package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.domain.service.interfaces.IRoleFactory;
import com.daily_expenses.domain.service.interfaces.IRoleService;
import com.daily_expenses.infrastructure.persistence.entity.RoleEnum;
import com.daily_expenses.util.RoleEnumUtil;
import com.daily_expenses.web.dto.RoleUpdateRequestDTO;
import com.daily_expenses.web.dto.RoleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IRoleFactory roleFactory;

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        String roleNameUpperCase = roleName.toUpperCase();
        return Optional.ofNullable(this.roleRepository.findByRoleName(roleNameUpperCase).orElseThrow(() -> new RuntimeException("Role not found in the database: " + roleNameUpperCase)));
    }

    @Override
    public RoleResponseDTO save(RoleUpdateRequestDTO roleCreateRequestDTO) {

        if (!RoleEnumUtil.isRoleEnumValid(roleCreateRequestDTO.roleName())) {
            throw new IllegalArgumentException("Role '" + roleCreateRequestDTO.roleName() + "' not found in RoleEnum");
        }

        Role role = this.roleFactory.createRole(roleCreateRequestDTO);
        Role roleSaved = this.roleRepository.save(role);
        return new RoleResponseDTO(
                roleSaved.getRoleName(),
                "Permissions assign successfully",
                roleSaved.getPermissionList().stream()
                        .map(Permission::getName)
                        .collect(Collectors.toList())
        );
    }

}
