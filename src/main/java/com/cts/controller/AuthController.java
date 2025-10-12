package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.Auth;
import com.cts.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management", description = "Create Web Api's for Login and Register")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<Auth>> getAll() {
        log.info("Fetching all users");
        List<Auth> authList = authService.getAll();
        log.debug("Fetched {} users", authList.size());
        return ResponseEntity.ok(authList);
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/{email}")
    public ResponseEntity<Auth> getByEmail(@PathVariable String email) {
        log.info("Fetching user by email: {}", email);
        Auth auth = authService.getByEmail(email);
        return ResponseEntity.ok(auth);
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Auth auth) {
        log.info("Registering user: {}", auth.getEmail());
        String result = authService.create(auth);
        log.info("Registration result: {}", result);
        return result.contains("successfully")
                ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{email}")
    public ResponseEntity<Auth> update(@PathVariable String email, @RequestBody Auth auth) {
        log.info("Updating user: {}", email);
        Auth updated = authService.update(email, auth);
        log.info("User {} updated successfully", email);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        log.info("Deleting user: {}", email);
        authService.delete(email);
        log.info("User {} deleted successfully", email);
        return ResponseEntity.ok("Deleted successfully");
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Auth auth) {
        log.info("Login attempt for user: {}", auth.getEmail());
        String result = authService.login(auth.getEmail(), auth.getPassword(), auth.getRole());
        log.info("Login result for {}: {}", auth.getEmail(), result);
        return result.contains("successful")
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
