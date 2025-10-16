package com.cts.controller;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.dto.LoginDTO;
import com.cts.dto.LoginResponseDTO;
import com.cts.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Management", description = "Create Web APIs for Login and Register")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @Operation(summary = "Get all users", description = "Fetches a list of all registered Auth users")
    @GetMapping
    public ResponseEntity<List<AuthResponseDTO>> getAll() {
        List<AuthResponseDTO> authList = authService.getAll();
        return ResponseEntity.ok(authList);
    }

    @Operation(summary = "Get user by email", description = "Fetches a single Auth user by their email address")
    @GetMapping("/{id}")
    public ResponseEntity<AuthResponseDTO> getByUserId(@PathVariable int  id) {
        AuthResponseDTO authDto = authService.getById(id);
        return ResponseEntity.ok(authDto);
    }

    @Operation(summary = "Register a new user", description = "Registers a new Auth user with email, password, and role")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequestDTO authDto) {
        String result = authService.create(authDto);
        HttpStatus status = result.contains("successfully") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(result);
    }

    @Operation(summary = "Update user", description = "Updates an existing Auth user by email")
    @PutMapping("/{id}")
    public ResponseEntity<AuthResponseDTO> update(@PathVariable int id,
                                                  @RequestBody @Valid AuthRequestDTO authDto) {
        AuthResponseDTO updatedDto = authService.update(id, authDto);
        return ResponseEntity.ok(updatedDto);
    }

    @Operation(summary = "Delete user", description = "Deletes an Auth user by email")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        authService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @Operation(summary = "Login user", description = "Authenticates a user with email and password")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDto) {
        LoginResponseDTO response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
