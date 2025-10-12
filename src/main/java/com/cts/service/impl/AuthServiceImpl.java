package com.cts.service.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.entity.Auth;
import com.cts.exception.MissingFieldException;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public List<Auth> getAll() {
        log.info("Retrieving all Auth users");
        return authRepository.findAll();
    }

    @Override
    public Auth getByEmail(String email) {
        log.info("Retrieving Auth user by email: {}", email);
        return authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
    }

    @Override
    public String create(Auth auth) {
        log.info("Creating new Auth user: {}", auth.getEmail());
        try {
            if (auth.getEmail() == null || auth.getEmail().trim().isEmpty()) {
                throw new MissingFieldException("Email is required");
            }
            if (auth.getPassword() == null || auth.getPassword().trim().isEmpty()) {
                throw new MissingFieldException("Password is required");
            }
            if (auth.getRole() == null || auth.getRole().trim().isEmpty()) {
                throw new MissingFieldException("Role is required");
            }

            if (authRepository.existsById(auth.getEmail())) {
                throw new MissingFieldException("Registration failed: Email already exists");
            }

            // Hash the password before saving
            auth.setPassword(passwordEncoder.encode(auth.getPassword()));

            authRepository.save(auth);
            return "Registered successfully";
        } catch (Exception e) {
            throw new RuntimeException("Unable to register: " + e.getMessage());
        }
    }

    @Override
    public Auth update(String email, Auth updatedAuth) {
        log.info("Updating user: {}", email);
        Auth existing = getByEmail(email);
        existing.setPassword(updatedAuth.getPassword());
        existing.setRole(updatedAuth.getRole());
        Auth saved = authRepository.save(existing);
        log.info("User {} updated successfully", email);
        return saved;
    }

    @Override
    public void delete(String email) {
        log.info("Deleting user: {}", email);
        Auth existing = getByEmail(email);
        authRepository.delete(existing);
        log.info("User {} deleted", email);
    }

    @Override
    public String login(String email, String rawPassword, String role) {
        log.info("Login attempt for email: {}", email);
        try {
            Auth auth = authRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

            if (!passwordEncoder.matches(rawPassword, auth.getPassword())) {
                return "Invalid credentials";
            }

            if (!auth.getRole().equalsIgnoreCase(role)) {
                return "Invalid role";
            }

            return "Login successful for role: " + auth.getRole();
        } catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }
}
