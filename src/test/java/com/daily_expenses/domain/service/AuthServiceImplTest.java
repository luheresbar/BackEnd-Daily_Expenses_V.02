package com.daily_expenses.domain.service;

import com.daily_expenses.infrastructure.security.JwtUtils;
import com.daily_expenses.infrastructure.security.UserDetailServiceImpl;
import com.daily_expenses.web.dto.AuthLoginRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailServiceImpl userDetailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    public void testLoginUser() {
        // Given
        String email = "jane.doe@example.com";
        String password = "password456";
        AuthLoginRequestDTO loginRequest = new AuthLoginRequestDTO(email, password);

        UserDetails userDetails = UserDataProvider.userDetailsMock();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        String accessToken = "accessToken";

        // When
        when(userDetailService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(true);
        when(jwtUtils.createToken(any(Authentication.class))).thenReturn(accessToken);

        AuthResponseDTO response = authService.loginUser(loginRequest);

        // Then
        assertNotNull(response);
        assertEquals(email, response.username());
        assertEquals("User logged successfully", response.message());
        assertEquals(accessToken, response.jwt());
        assertTrue(response.status());

        verify(userDetailService).loadUserByUsername(email);
        verify(passwordEncoder).matches(password, userDetails.getPassword());
        verify(jwtUtils).createToken(any(Authentication.class));
    }



    @Test
    public void testAuthenticateSuccess() {
        // Given
        String email = "jane.doe@example.com";
        String password = "password456";
        UserDetails userDetails = UserDataProvider.userDetailsMock();

        // When
        when(userDetailService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(true);

        Authentication authentication = authService.authenticate(email, password);

        // Then
        assertNotNull(authentication);
        assertEquals(userDetails.getUsername(), authentication.getName());
        assertEquals(new HashSet<>(userDetails.getAuthorities()), new HashSet<>(authentication.getAuthorities()));
        verify(userDetailService).loadUserByUsername(email);
        verify(passwordEncoder).matches(password, userDetails.getPassword());
    }

    @Test
    public void testLoginUserBadCredentials() {
        // Given
        String email = "jane.doe@example.com";
        String password = "wrongPassword";
        AuthLoginRequestDTO loginRequest = new AuthLoginRequestDTO(email, password);

        UserDetails userDetails = UserDataProvider.userDetailsMock();

        // When
        when(userDetailService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(false);

        // Then
        assertThrows(BadCredentialsException.class, () -> authService.loginUser(loginRequest));
        verify(userDetailService).loadUserByUsername(email);
        verify(passwordEncoder).matches(password, userDetails.getPassword());
    }

    @Test
    public void testAuthenticateFailure() {
        // Given
        String email = "jane.doe@example.com";
        String password = "wrongPassword";
        UserDetails userDetails = UserDataProvider.userDetailsMock();

        // When
        when(userDetailService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(false);

        // Then
        assertThrows(BadCredentialsException.class, () -> authService.authenticate(email, password));
        verify(userDetailService).loadUserByUsername(email);
        verify(passwordEncoder).matches(password, userDetails.getPassword());
    }

    @Test
    public void testSetAuthentication() {
        // Given
        Authentication authentication = mock(Authentication.class);

        // When
        authService.setAuthentication(authentication);

        // Then
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }


}
