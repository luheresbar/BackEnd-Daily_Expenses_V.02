package com.daily_expenses.domain.service.implementation;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.domain.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
