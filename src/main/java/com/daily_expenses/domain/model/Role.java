package com.daily_expenses.domain.model;

import com.daily_expenses.infrastructure.persistence.entity.RoleEnum;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long id;
    private String roleName;
    private Set<Permission> permissionList;

}
