package com.daily_expenses.domain.service;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

public class UserDataProvider {

    public static List<User> userListMock() {
        List<Permission> permissions = PermissionDataProvider.permissionListMock();
        Role userRole = new Role(1L, "USER", Set.of(permissions.get(1))); // READ
        Role adminRole = new Role(2L, "ADMIN", Set.of(permissions.get(1), permissions.get(2))); // READ, UPDATE

        return List.of(
                new User(1L, "john_doe", "john.doe@example.com", "password123", LocalDateTime.now(), true, true, true, true, Set.of(userRole)),
                new User(2L, "jane_doe", "jane.doe@example.com", "password456", LocalDateTime.now(), true, true, true, true, Set.of(adminRole)),
                new User(3L, "alice_smith", "alice.smith@example.com", "password789", LocalDateTime.now(), true, true, true, true, Set.of(userRole, adminRole))
        );
    }

    public static Optional<User> userMock() {
        List<Permission> permissions = PermissionDataProvider.permissionListMock();
        Role adminRole = new Role(2L, "ADMIN", Set.of(permissions.get(1), permissions.get(2))); // READ, UPDATE

        return Optional.of(new User(1L, "jane_doe", "jane.doe@example.com", "password456", LocalDateTime.now(), true, true, true, true, Set.of(adminRole)));
    }

    public static User newUserMock() {
        List<Permission> permissions = PermissionDataProvider.permissionListMock();
        Role userRole = new Role(1L, "USER", Set.of(permissions.get(1))); // READ

        return new User(10L, "Luis Diaz", "luis.d@gmail.com", "password123", LocalDateTime.now(), true, true, true, true, Set.of(userRole));
    }


    public static UserDetails userDetailsMock() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("READ"));
        authorities.add(new SimpleGrantedAuthority("UPDATE"));
        authorities.add(new SimpleGrantedAuthority("CREATE"));
        authorities.add(new SimpleGrantedAuthority("DELETE"));

        return org.springframework.security.core.userdetails.User.withUsername("jane.doe@example.com")
                .password("encodedPassword456")
                .authorities(authorities)
                .build();
    }
}
