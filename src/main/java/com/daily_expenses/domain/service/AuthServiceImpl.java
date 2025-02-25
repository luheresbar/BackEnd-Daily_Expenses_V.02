package com.daily_expenses.domain.service;

import com.daily_expenses.domain.exception.UserNotFoundException;
import com.daily_expenses.domain.service.interfaces.IAuthService;
import com.daily_expenses.infrastructure.security.JwtUtils;
import com.daily_expenses.infrastructure.security.UserDetailServiceImpl;
import com.daily_expenses.web.dto.AuthLoginRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {
        String email = authLoginRequest.email();
        String password = authLoginRequest.password();

        try {
            Authentication authentication = this.authenticate(email, password);
            setAuthentication(authentication);

            String accessToken = jwtUtils.createToken(authentication);
            return new AuthResponseDTO(authentication.getName(), "User logged successfully", accessToken, true);

        } catch (UsernameNotFoundException ex) {
            throw new UserNotFoundException(ex.getMessage());
        }
    }

    @Override
    public Authentication authenticate(String email, String password) {

        UserDetails userDetails = this.userDetailService.loadUserByUsername(email);
        String userId = userDetails.getUsername();

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(userId, null, userDetails.getAuthorities());

    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public Long getAuthenticatedUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }


}
