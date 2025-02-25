package com.daily_expenses.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime registerDate;
    private boolean enabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
    private Set<Role> roles;
}
