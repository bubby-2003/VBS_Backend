package com.cts.controller;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO; 
import com.cts.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; 

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management",description = "Create Web Api's for Login and Register")
public class AuthController {

    @Autowired
    private AuthService authService;

    // --- GET ALL USERS ---
    @Operation(summary = "Get all users", description = "Fetches a list of all registered Auth users")
    @GetMapping
    public ResponseEntity<List<AuthResponseDTO>> getAll() {
        // Service returns List<AuthResponseDTO>
        List<AuthResponseDTO> authList = authService.getAll(); 
        return ResponseEntity.ok(authList);
    }

    // --- GET USER BY EMAIL ---
    @Operation(summary = "Get user by email", description = "Fetches a single Auth user by their email address")
    @GetMapping("/{email}")
    public ResponseEntity<AuthResponseDTO> getByEmail(@PathVariable String email) {
        // Service returns AuthResponseDTO
        AuthResponseDTO authDto = authService.getByEmail(email);
        return ResponseEntity.ok(authDto);
    }

    // --- REGISTER NEW USER ---
    @Operation(summary = "Register a new user", description = "Registers a new Auth user with email, password, and role")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequestDTO authDto) {
        // Service accepts AuthRequestDTO
        String result = authService.create(authDto);
        if (result.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    // --- UPDATE USER ---
    @Operation(summary = "Update user", description = "Updates an existing Auth user by email")
    @PutMapping("/{email}")
    public ResponseEntity<AuthResponseDTO> update(@PathVariable String email, 
                                                  @RequestBody @Valid AuthRequestDTO authDto) {
        // Service accepts AuthRequestDTO and returns AuthResponseDTO
        AuthResponseDTO updatedDto = authService.update(email, authDto);
        return ResponseEntity.ok(updatedDto);
    }

    // --- DELETE USER ---
    @Operation(summary = "Delete user", description = "Deletes an Auth user by email")
    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        authService.delete(email);
        return ResponseEntity.ok("Deleted successfully");
    }

    // --- LOGIN USER ---
    @Operation(summary = "Login user", description = "Authenticates a user with email, password, and role")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO authDto) {
        // Service accepts AuthRequestDTO
        String result = authService.login(authDto);
        
        // Assuming the service returns a JWT token or success message on success
        if (result.contains("successful") || result.contains("TOKEN_FOR_")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }
}