package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.domain.service.interfaces.IUserFactory;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserFactory implements IUserFactory {

    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(AuthCreateUserRequestDTO createUserRequest) {
        String username = createUserRequest.username();
        String email = createUserRequest.email();
        String password = createUserRequest.password();
        List<String> rolesRequest = createUserRequest.roleListName();

        // Fetch all valid roles from the database
        List<Role> allRoles = this.roleRepository.findAll();
        Set<String> validRoleEnums = allRoles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        // Check if any requested role does not exist
        for (String roleName : rolesRequest) {
            if (!validRoleEnums.contains(roleName)) {
                throw new IllegalArgumentException("Role not found: " + roleName);
            }
        }

        // Filter the roles that match the requested roles
        Set<Role> roleList = allRoles.stream()
                .filter(role -> rolesRequest.contains(role.getRoleName()))
                .collect(Collectors.toSet());

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .roles(roleList)
                .enabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true).build();
    }

}
