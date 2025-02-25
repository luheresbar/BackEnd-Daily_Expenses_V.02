package com.daily_expenses.domain.service;

import com.daily_expenses.domain.exception.UserNotFoundException;
import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.domain.service.interfaces.IAuthService;
import com.daily_expenses.domain.service.interfaces.IUserFactory;
import com.daily_expenses.domain.service.interfaces.IUserService;
import com.daily_expenses.infrastructure.security.JwtUtils;
import com.daily_expenses.infrastructure.security.UserDetailServiceImpl;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import com.daily_expenses.web.dto.UserUpdateRolesDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserFactory userFactory;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private IAuthService authService;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email)));
    }

    @Override
    public Boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public AuthResponseDTO createUser(AuthCreateUserRequestDTO createUserRequest) {

        if (this.existsByEmail(createUserRequest.email())) {
            throw new IllegalArgumentException("Email '" + createUserRequest.email() + "' already exists");
        }

        User user = this.userFactory.createUser(createUserRequest);
        System.out.println(user + " User en user Factory");
        User savedUser = this.userRepository.save(user);
        System.out.println(savedUser + " User en user Saved");

        UserDetails userDetails = this.userDetailService.buildUserDetails(savedUser);
        String userId = userDetails.getUsername();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, userDetails.getAuthorities());
        this.authService.setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponseDTO(savedUser.getUsername(), "User created successfully", accessToken, true);
    }

    @Override
    public UserUpdateRolesDTO updateUserRoles(UserUpdateRolesDTO updateUserRolesDTO) {

        String email = updateUserRolesDTO.email();
        List<String> rolesRequest = updateUserRolesDTO.roles();

        User dbUser = this.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

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

        // Update User Roles
        dbUser.setRoles(roleList);

        User updatedUser = this.userRepository.save(dbUser);

        List<String> roleListRequest = updatedUser.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        // Devuelve el DTO actualizado
        return new UserUpdateRolesDTO(updatedUser.getEmail(), roleListRequest, Optional.of("User roles updated successfully"));
    }

}
