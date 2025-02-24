package com.daily_expenses.web.controller;


import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.service.UserDataProvider;
import com.daily_expenses.domain.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testFindAll() {
        // Given - Then
        when(this.userService.findAll()).thenReturn(UserDataProvider.userListMock());
        ResponseEntity<List<User>> result = this.userController.findAll();

        // Then
        assertNotNull(result);
        assertFalse(Objects.requireNonNull(result.getBody()).isEmpty());
        assertEquals("john_doe", result.getBody().get(0).getUsername());

    }
}
