package com.cts.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.entity.Auth;
import com.cts.enums.AuthRole;
import com.cts.exception.MissingFieldException;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passenc;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AuthResponseDTO> getAll() {
        return authRepository.findAll().stream()
                .map(auth -> modelMapper.map(auth, AuthResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AuthResponseDTO getByEmail(String email) {
        Auth auth = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
        return modelMapper.map(auth, AuthResponseDTO.class);
    }

    @Override
    public String create(AuthRequestDTO authDto) {
        AuthRole role = authDto.getRole();

        if (role == AuthRole.ADMIN) {
            throw new MissingFieldException("Registration failed: Admin role is not allowed to self-register");
        }

        if (authRepository.existsById(authDto.getEmail())) {
            throw new MissingFieldException("Registration failed: Email already exists");
        }

        Auth auth = modelMapper.map(authDto, Auth.class);
        auth.setPassword(passenc.encode(authDto.getPassword()));
        auth.setRole(role);

        authRepository.save(auth);
        return "Registered successfully";
    }

    @Override
    public AuthResponseDTO update(String email, AuthRequestDTO authDto) {
        Auth existing = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));

        if (authDto.getPassword() != null && !authDto.getPassword().trim().isEmpty()) {
            existing.setPassword(passenc.encode(authDto.getPassword()));
        }

        Auth updated = authRepository.save(existing);
        return modelMapper.map(updated, AuthResponseDTO.class);
    }

    @Override
    public void delete(String email) {
        Auth existing = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
        authRepository.delete(existing);
    }

    @Override
    public String login(AuthRequestDTO authDto) {
        Auth auth = authRepository.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        if (!passenc.matches(authDto.getPassword(), auth.getPassword())) {
            throw new ResourceNotFoundException("Invalid credentials");
        }

        return "TOKEN_FOR_" + auth.getEmail() + "_ROLE_" + auth.getRole();
    }
}
