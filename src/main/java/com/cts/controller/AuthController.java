package com.cts.controller;
 
import com.cts.entity.Auth;
import com.cts.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/auth")
public class AuthController {
 
    @Autowired
    private AuthService authService;
 
    @GetMapping
    public ResponseEntity<List<Auth>> getAll() {
        List<Auth> authList = authService.getAll();
        return ResponseEntity.ok(authList);
    }
 
    @GetMapping("/{email}")
    public ResponseEntity<Auth> getByEmail(@PathVariable String email) {
        Auth auth = authService.getByEmail(email);
        return ResponseEntity.ok(auth);
    }
 
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Auth auth) {
        String result = authService.create(auth);
        if (result.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
 
    @PutMapping("/{email}")
    public ResponseEntity<Auth> update(@PathVariable String email, @RequestBody Auth auth) {
        Auth updated = authService.update(email, auth);
        return ResponseEntity.ok(updated);
    }
 
    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        authService.delete(email);
        return ResponseEntity.ok("Deleted successfully");
    }
 
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