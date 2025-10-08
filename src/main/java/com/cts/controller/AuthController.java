package com.cts.controller;

import com.cts.entity.Auth;
import com.cts.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management",description = "Create Web Api's for Login and Register")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Get all users", description = "Fetches a list of all registered Auth users")
    @GetMapping
    public ResponseEntity<List<Auth>> getAll() {
        List<Auth> authList = authService.getAll();
        return ResponseEntity.ok(authList);
    }

    @Operation(summary = "Get user by email", description = "Fetches a single Auth user by their email address")
    @GetMapping("/{email}")
    public ResponseEntity<Auth> getByEmail(@PathVariable String email) {
        Auth auth = authService.getByEmail(email);
        return ResponseEntity.ok(auth);
    }

    @Operation(summary = "Register a new user", description = "Registers a new Auth user with email, password, and role")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Auth auth) {
        String result = authService.create(auth);
        if (result.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @Operation(summary = "Update user", description = "Updates an existing Auth user by email")
    @PutMapping("/{email}")
    public ResponseEntity<Auth> update(@PathVariable String email, @RequestBody Auth auth) {
        Auth updated = authService.update(email, auth);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete user", description = "Deletes an Auth user by email")
    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        authService.delete(email);
        return ResponseEntity.ok("Deleted successfully");
    }

    @Operation(summary = "Login user", description = "Authenticates a user with email, password, and role")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Auth auth) {
        String result = authService.login(auth.getEmail(), auth.getPassword(), auth.getRole());
        if (result.contains("successful")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }
}
