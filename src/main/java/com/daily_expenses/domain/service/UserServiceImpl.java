package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.domain.service.interfaces.IUserFactory;
import com.daily_expenses.domain.service.interfaces.IUserService;
import com.daily_expenses.infrastructure.security.JwtTokenProvider;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl  implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserFactory userFactory;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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
        System.out.println(user + " User en service"); //TODO: remove
        User savedUser = this.userRepository.save(user);
        System.out.println(savedUser + " SavedUser en service"); //TODO: remove
        String accessToken = this.jwtTokenProvider.createToken(savedUser);

        return new AuthResponseDTO(savedUser.getUsername(), "User created successfully", accessToken, true);
    }

}
