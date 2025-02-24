package com.daily_expenses.web.controller;

import com.daily_expenses.domain.model.User;
import com.daily_expenses.domain.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/find/all")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.findById(id));
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<Optional<User>> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(this.userService.findByEmail(email));
    }
}
