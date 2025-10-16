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
@Tag(name = "Authentication Management", description = "APIs for Login and Register")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<AuthResponseDTO>> getAll() {
        return ResponseEntity.ok(authService.getAll());
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AuthResponseDTO> getByUserId(@PathVariable int id) {
        return ResponseEntity.ok(authService.getById(id));
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequestDTO authDto) {
        String result = authService.create(authDto);
        HttpStatus status = result.contains("successfully") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(result);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        authService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
