package com.daily_expenses.util;

import com.daily_expenses.infrastructure.persistence.entity.RoleEnum;
import org.springframework.stereotype.Component;

@Component
public class RoleEnumUtil {
    public static boolean isRoleEnumValid(String roleName) {
        try {
            RoleEnum.valueOf(roleName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static RoleEnum convertStringToRoleEnum(String roleName) {
        try {
            return RoleEnum.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role '" + roleName + "' not found in RoleEnum");
        }
    }
}