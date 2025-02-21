package com.daily_expenses.domain.service;

import com.daily_expenses.domain.service.interfaces.IAuthService;
import com.daily_expenses.infrastructure.security.JwtUtils;
import com.daily_expenses.infrastructure.security.UserDetailServiceImpl;
import com.daily_expenses.web.dto.AuthLoginRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

        Authentication authentication = this.authenticate(email, password);
        setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponseDTO(authentication.getName(), "User logged successfully", accessToken, true);
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
}
