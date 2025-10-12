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



@Operation(summary = "Get all users", description = "Fetches a list of all registered Auth users")

@GetMapping

public ResponseEntity<List<AuthResponseDTO>> getAll() {

List<AuthResponseDTO> authList = authService.getAll();
return ResponseEntity.ok(authList);
}


@Operation(summary = "Get user by email", description = "Fetches a single Auth user by their email address")

@GetMapping("/{email}")
public ResponseEntity<AuthResponseDTO> getByEmail(@PathVariable String email) {
AuthResponseDTO authDto = authService.getByEmail(email);
return ResponseEntity.ok(authDto);
}



 @Operation(summary = "Register a new user", description = "Registers a new Auth user with email, password, and role")

@PostMapping("/register")

 public ResponseEntity<String> register(@RequestBody @Valid AuthRequestDTO authDto) {

 String result = authService.create(authDto);

if (result.contains("successfully")) {

return ResponseEntity.status(HttpStatus.CREATED).body(result);
 } else {

return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
}
}



 @Operation(summary = "Update user", description = "Updates an existing Auth user by email")
 @PutMapping("/{email}")

public ResponseEntity<AuthResponseDTO> update(@PathVariable String email,

@RequestBody @Valid AuthRequestDTO authDto) {

 AuthResponseDTO updatedDto = authService.update(email, authDto);

 return ResponseEntity.ok(updatedDto);

}

 @Operation(summary = "Delete user", description = "Deletes an Auth user by email")

 @DeleteMapping("/{email}")

public ResponseEntity<String> delete(@PathVariable String email) {

authService.delete(email);
 return ResponseEntity.ok("Deleted successfully");
}



 @Operation(summary = "Login user", description = "Authenticates a user with email, password, and role")

 @PostMapping("/login")

 public ResponseEntity<String> login(@RequestBody @Valid AuthRequestDTO authDto) {

 String result = authService.login(authDto);

 if (result.contains("successful") || result.contains("token:")) {

 return ResponseEntity.ok(result);

 } else {

 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
 }

}

}