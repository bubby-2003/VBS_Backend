package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management", description = "Create Web APIs for Login and Register")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<AuthResponseDTO>> getAll() {
        log.info("Fetching all users");
        List<AuthResponseDTO> authList = authService.getAll();
        log.debug("Fetched {} users", authList.size());
        return ResponseEntity.ok(authList);
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/{email}")
    public ResponseEntity<AuthResponseDTO> getByEmail(@PathVariable String email) {
        log.info("Fetching user by email: {}", email);
        AuthResponseDTO auth = authService.getByEmail(email);
        return ResponseEntity.ok(auth);
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequestDTO dto) {
        log.info("Registering user: {}", dto.getEmail());
        String result = authService.create(dto);
        return result.contains("successfully")
                ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{email}")
    public ResponseEntity<AuthResponseDTO> update(@PathVariable String email, @Valid @RequestBody AuthRequestDTO dto) {
        log.info("Updating user: {}", email);
        AuthResponseDTO updated = authService.update(email, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        log.info("Deleting user: {}", email);
        authService.delete(email);
        return ResponseEntity.ok("Deleted successfully");
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequestDTO dto) {
        log.info("Login attempt for user: {}", dto.getEmail());
        String result = authService.login(dto);
        return result.contains("successful")
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}