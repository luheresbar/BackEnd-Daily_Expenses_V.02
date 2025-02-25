package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IUserRepository;
import com.daily_expenses.domain.service.interfaces.IAuthService;
import com.daily_expenses.domain.service.interfaces.IUserFactory;
import com.daily_expenses.infrastructure.security.JwtUtils;
import com.daily_expenses.infrastructure.security.UserDetailServiceImpl;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import com.daily_expenses.web.dto.AuthResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserFactory userFactory;

    @Mock
    private UserDetailServiceImpl userDetailService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private IAuthService authService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFindAll() {
        // Given - Then
        when(userRepository.findAll()).thenReturn(UserDataProvider.userListMock());
        List<User> result = userService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("john_doe", result.get(0).getUsername());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Given
        Long id = 1L;

        // When
        when(userRepository.findById(anyLong())).thenReturn(UserDataProvider.userMock());
        Optional<User> user = userService.findById(id);

        // Then
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals("jane_doe", user.get().getUsername());
        assertEquals("jane.doe@example.com", user.get().getEmail());
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void testFindByIdNotFound() {
        // Given
        Long id = 1L;

        // When
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<User> user = userService.findById(id);

        // Then
        assertNotNull(user);
        assertTrue(user.isEmpty());
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void testFindByEmail() {
        // Given
        String email = "jane.doe@example.com";

        // When
        when(userRepository.findByEmail(anyString())).thenReturn(UserDataProvider.userMock());
        Optional<User> user = userService.findByEmail(email);

        // Then
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals("jane_doe", user.get().getUsername());
        assertEquals(email, user.get().getEmail());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    public void testFindByEmailNotFound() {
        // Given
        String email = "nonexistent@example.com";

        // When
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Optional<User> user = userService.findByEmail(email);

        // Then
        assertNotNull(user);
        assertTrue(user.isEmpty());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    public void testCreateUser() {
        // Given
        String username = "Luis Diaz";
        String email = "luis.d@gmail.com";
        String password = "password123";
        List<String> roleListName = List.of("USER");
        AuthCreateUserRequestDTO createUserRequest = new AuthCreateUserRequestDTO(username, email, password, roleListName);

        User newUser = UserDataProvider.newUserMock();
        User savedUser = UserDataProvider.newUserMock();
        UserDetails userDetails = mock(UserDetails.class);
        String accessToken = "accessToken";

        // When
        when(userFactory.createUser(any(AuthCreateUserRequestDTO.class))).thenReturn(newUser);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userDetailService.buildUserDetails(any(User.class))).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("userId");
        when(jwtUtils.createToken(any(Authentication.class))).thenReturn(accessToken);

        AuthResponseDTO response = userService.createUser(createUserRequest);

        // Then
        assertNotNull(response);
        assertEquals(username, response.username());
        assertEquals("User created successfully", response.message());
        assertEquals(accessToken, response.jwt());
        assertTrue(response.status());

        verify(userFactory).createUser(createUserRequest);
        verify(userRepository).save(newUser);
        verify(userDetailService).buildUserDetails(savedUser);
        verify(authService).setAuthentication(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).createToken(any(Authentication.class));
    }
}
