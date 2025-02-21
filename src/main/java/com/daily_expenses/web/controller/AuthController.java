package com.daily_expenses.web.controller;

import com.daily_expenses.domain.service.interfaces.IAuthService;
import com.daily_expenses.domain.service.interfaces.IUserService;
import com.daily_expenses.infrastructure.security.UserDetailServiceImpl;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import com.daily_expenses.web.dto.AuthLoginRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAuthService authService;

    @PostMapping("/sign-up")
    @Transactional
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid AuthCreateUserRequestDTO userRequest){
        return new ResponseEntity<>(this.userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest){
        return new ResponseEntity<>(this.authService.loginUser(userRequest), HttpStatus.OK);
    }

}
