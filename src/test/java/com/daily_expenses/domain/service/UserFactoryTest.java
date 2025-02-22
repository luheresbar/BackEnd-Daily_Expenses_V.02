package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.repository.IRoleRepository;
import com.daily_expenses.domain.service.interfaces.IUserFactory;
import com.daily_expenses.web.dto.AuthCreateRoleRequestDTO;
import com.daily_expenses.web.dto.AuthCreateUserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserFactoryTest {

    @Mock
    private IRoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserFactory userFactory;

    @Test
    public void testCreateUser() {
        // Given
        String username = "Luis Diaz";
        String email = "luis.d@gmail.com";
        String password = "password123";
        List<String> rolesRequest = List.of("USER");
        AuthCreateRoleRequestDTO roleRequest = new AuthCreateRoleRequestDTO(rolesRequest);
        AuthCreateUserRequestDTO createUserRequest = new AuthCreateUserRequestDTO(username, email, password, roleRequest);

        List<Role> allRoles = RoleDataProvider.roleListMock();
        Role userRole = allRoles.stream().filter(role -> "USER".equals(role.getRoleEnum())).findFirst().orElseThrow();

        // When
        when(roleRepository.findAll()).thenReturn(allRoles);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword123");

        User user = userFactory.createUser(createUserRequest);

        // Then
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals("encodedPassword123", user.getPassword());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNoLocked());
        assertTrue(user.isAccountNoExpired());
        assertTrue(user.isCredentialNoExpired());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(userRole));

        verify(roleRepository).findAll();
        verify(passwordEncoder).encode(password);
    }

    @Test
    public void testCreateUserRoleNotFound() {
        // Given
        String username = "Luis Diaz";
        String email = "luis.d@gmail.com";
        String password = "password123";
        List<String> rolesRequest = List.of("NON_EXISTENT_ROLE");
        AuthCreateRoleRequestDTO roleRequest = new AuthCreateRoleRequestDTO(rolesRequest);
        AuthCreateUserRequestDTO createUserRequest = new AuthCreateUserRequestDTO(username, email, password, roleRequest);

        List<Role> allRoles = RoleDataProvider.roleListMock();

        // When
        when(roleRepository.findAll()).thenReturn(allRoles);

        // Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userFactory.createUser(createUserRequest);
        });

        assertEquals("Role not found: NON_EXISTENT_ROLE", exception.getMessage());
        verify(roleRepository).findAll();
        verify(passwordEncoder, never()).encode(anyString());
    }


}
