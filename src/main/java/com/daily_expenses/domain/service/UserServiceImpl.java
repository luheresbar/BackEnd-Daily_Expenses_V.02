package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.domain.service.interfaces.IAuthService;
import com.daily_expenses.domain.service.interfaces.IUserFactory;
import com.daily_expenses.domain.service.interfaces.IUserService;
import com.daily_expenses.infrastructure.security.JwtUtils;
import com.daily_expenses.infrastructure.security.UserDetailServiceImpl;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl  implements IUserService {

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

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public AuthResponseDTO createUser(AuthCreateUserRequestDTO createUserRequest) {
        User user = this.userFactory.createUser(createUserRequest);
        User savedUser = this.userRepository.save(user);

        UserDetails userDetails = this.userDetailService.buildUserDetails(savedUser);
        String userId = userDetails.getUsername();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, userDetails.getAuthorities());
        this.authService.setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponseDTO(savedUser.getUsername(), "User created successfully", accessToken, true);
    }

}
