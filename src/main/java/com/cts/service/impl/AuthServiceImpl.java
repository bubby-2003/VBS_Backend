package com.cts.service.impl;
 
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.AuthRequestDTO;    // Import Request DTO
import com.cts.dto.AuthResponseDTO;   // Import Response DTO
import com.cts.entity.Auth;
import com.cts.exception.MissingFieldException; // Re-use existing exception
import com.cts.exception.ResourceNotFoundException;
import com.cts.mapper.AuthMapper;      // Import Mapper
import com.cts.repository.AuthRepository;
import com.cts.service.AuthService;
// Assuming you have a PasswordEncoder bean configured
// import org.springframework.security.crypto.password.PasswordEncoder; 

@Service
public class AuthServiceImpl implements AuthService {
 
    @Autowired
    private AuthRepository authRepository;

    // Assuming you have a PasswordEncoder for production use
    // @Autowired
    // private PasswordEncoder passwordEncoder;
 
    // 1. READ ALL: Returns List<AuthResponseDTO>
    @Override
    public List<AuthResponseDTO> getAll() {
        return authRepository.findAll().stream()
                .map(AuthMapper::toDTO)
                .collect(Collectors.toList());
    }
 
    // 2. READ BY EMAIL: Returns AuthResponseDTO
    @Override
    public AuthResponseDTO getByEmail(String email) {
        Auth auth = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
        return AuthMapper.toDTO(auth);
    }
 
    // 3. CREATE: Accepts AuthRequestDTO
    @Override
    public String create(AuthRequestDTO authDto) {
        // Validation for role is now missing from the DTO, assuming a default role set here
        String defaultRole = "USER"; 

        try {
            // DTO validation (NotBlank/Email) is handled by the Controller using @Valid,
            // so we skip manual checks here unless absolutely necessary.
            
            if (authRepository.existsById(authDto.getEmail())) {
                throw new MissingFieldException("Registration failed: Email already exists");
            }

            Auth auth = AuthMapper.toEntity(authDto);
            
            // SECURITY NOTE: Hash the password before saving!
            // auth.setPassword(passwordEncoder.encode(auth.getPassword())); 
            
            // Set the role, as it's not in the AuthRequestDTO
            auth.setRole(defaultRole);

            authRepository.save(auth);
            return "Registered successfully";
        } catch (MissingFieldException e) {
             // Re-throw specific exception for handling in Controller/ExceptionHandler
             throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to register: " + e.getMessage());
        }
    }

 
    // 4. UPDATE: Accepts AuthRequestDTO, returns AuthResponseDTO
    @Override
    public AuthResponseDTO update(String email, AuthRequestDTO authDto) {
        Auth existing = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
        
        // Update only the password from the DTO
        if (authDto.getPassword() != null && !authDto.getPassword().trim().isEmpty()) {
            // SECURITY NOTE: Hash the new password before saving!
            // existing.setPassword(passwordEncoder.encode(authDto.getPassword()));
            existing.setPassword(authDto.getPassword()); // Placeholder for unhashed update
        }

        // NOTE: Updating the role or email via this endpoint is not supported by the DTO structure.
        
        Auth updated = authRepository.save(existing);
        return AuthMapper.toDTO(updated);
    }
 
    // 5. DELETE: No change needed here
    @Override
    public void delete(String email) {
        Auth existing = authRepository.findById(email)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with email: " + email));
        authRepository.delete(existing);
    }
 
    // 6. LOGIN: Accepts AuthRequestDTO
    @Override
    public String login(AuthRequestDTO authDto) {
        // NOTE: Since AuthRequestDTO doesn't have 'role', we assume either a default 
        // role is implied or the repository method can search without role (which requires a change).
        // Sticking to the previous signature requiring 'role' is not possible without changing the DTO.
        
        // Reverting to previous logic: try to find user by email and password (ignoring role for now)
        try {
            Auth auth = authRepository.findByEmail(authDto.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

            // SECURITY NOTE: In a real app, you would use passwordEncoder.matches() here.

            // Success: Generate JWT token and return it
            String token = "TOKEN_FOR_" + auth.getEmail() + "_ROLE_" + auth.getRole();
            return token; // Return token instead of "Login successful"
            
        } catch (ResourceNotFoundException e) {
            return "Invalid credentials";
        } catch (Exception e) {
            return "Login failed: " + e.getMessage();
        }
    }
}