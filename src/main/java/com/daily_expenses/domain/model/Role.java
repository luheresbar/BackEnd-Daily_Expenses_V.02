package com.daily_expenses.domain.model;

import com.daily_expenses.infrastructure.persistence.entity.RoleEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Role {

    private Long id;
    private String roleEnum;
    private Set<Permission> permissionList;

}
