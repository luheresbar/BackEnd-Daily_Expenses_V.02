package com.daily_expenses.web.controller;

import com.daily_expenses.domain.model.Permission;
import com.daily_expenses.domain.service.interfaces.IPermissionService;
import com.daily_expenses.web.dto.PermissionCreateRequestDTO;
import com.daily_expenses.web.dto.PermissionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/find/all")
    public ResponseEntity<List<Permission>> findAll() {
        return ResponseEntity.ok(this.permissionService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<PermissionResponseDTO> create(@RequestBody PermissionCreateRequestDTO permissionName) {
        return ResponseEntity.ok(this.permissionService.create(permissionName.permissionName()));
    }
}
