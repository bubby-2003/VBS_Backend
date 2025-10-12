package com.cts.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.entity.Auth;
import com.cts.exception.MissingFieldException;
import com.cts.exception.ResourceNotFoundException;
import com.cts.mapper.AuthMapper;
import com.cts.repository.AuthRepository;
import com.cts.service.AuthService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AuthResponseDTO> getAll() {
        return authRepository.findAll()
                .stream()
                .map(AuthMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthResponseDTO getByEmail(String email) {
        Auth auth = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return AuthMapper.toDTO(auth);
    }

    @Override
    public String create(AuthRequestDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new MissingFieldException("Email is required");
        }
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new MissingFieldException("Password is required");
        }
        if (dto.getRole() == null || dto.getRole().isBlank()) {
            throw new MissingFieldException("Role is required");
        }

        if (authRepository.existsById(dto.getEmail())) {
            throw new MissingFieldException("Registration failed: Email already exists");
        }

        Auth auth = AuthMapper.toEntity(dto);
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setRole(dto.getRole());

        authRepository.save(auth);
        return "Registered successfully";
    }

    @Override
    public AuthResponseDTO update(String email, AuthRequestDTO dto) {
        Auth existing = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRole() != null && !dto.getRole().isBlank()) {
            existing.setRole(dto.getRole());
        }

        Auth updated = authRepository.save(existing);
        return AuthMapper.toDTO(updated);
    }

    @Override
    public void delete(String email) {
        Auth existing = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        authRepository.delete(existing);
    }

    @Override
    public String login(AuthRequestDTO dto) {
        Auth auth = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), auth.getPassword())) {
            throw new ResourceNotFoundException("Invalid credentials");
        }

        if (!auth.getRole().equalsIgnoreCase(dto.getRole())) {
            throw new ResourceNotFoundException("Invalid role");
        }

        return "Login successful for role: " + auth.getRole();
    }
}