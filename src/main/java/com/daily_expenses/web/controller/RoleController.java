package com.daily_expenses.web.controller;

import com.daily_expenses.domain.model.Role;
import com.daily_expenses.domain.service.interfaces.IRoleService;
import com.daily_expenses.web.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/find/all")
    public ResponseEntity<List<Role>> createRole() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/find/name/{roleName}")
    public ResponseEntity<Optional<Role>> findRoleByName(@PathVariable String roleName) {
        return ResponseEntity.ok(roleService.findByRoleName(roleName));
    }

    @PostMapping("/update")
    public ResponseEntity<RoleDTO> assignPermissions(@RequestBody RoleDTO roleCreateRequestDTO) {
        return ResponseEntity.ok(this.roleService.save(roleCreateRequestDTO));
    }


}
