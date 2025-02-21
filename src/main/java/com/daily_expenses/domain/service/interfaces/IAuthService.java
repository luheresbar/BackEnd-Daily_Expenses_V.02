package com.daily_expenses.domain.service.interfaces;

import com.daily_expenses.web.dto.AuthLoginRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import org.springframework.security.core.Authentication;

public interface IAuthService {

    AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest);
    Authentication authenticate(String email, String password);
}
