package com.cts.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.config.JWTUtil;
import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.dto.LoginDTO;
import com.cts.dto.LoginResponseDTO;
import com.cts.entity.Auth;
import com.cts.enums.AuthRole;
import com.cts.exception.MissingFieldException;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.service.AuthService;
import com.cts.service.UserInfoConfigManager;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	
    private JWTUtil jwtUtil;
    private AuthRepository authRepository;
    private PasswordEncoder passenc;
    private ModelMapper modelMapper;
    private AuthenticationManager authenticationManager;
    private UserInfoConfigManager userInfoConfigManager;

    @Override
    public List<AuthResponseDTO> getAll() {
        return authRepository.findAll().stream()
                .map(auth -> modelMapper.map(auth, AuthResponseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public AuthResponseDTO getById(int id) {
        Auth auth = authRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with Id: " + id));
        return modelMapper.map(auth, AuthResponseDTO.class);
    }
    
    @Override
    public String create(AuthRequestDTO authDto) {
        AuthRole role = authDto.getRole();

        if (role == AuthRole.ADMIN) {
            throw new MissingFieldException("Registration failed: Admin role is not allowed to self-register");
        }
        if (authRepository.findByEmail(authDto.getEmail()).isPresent()) {
            throw new ResourceNotFoundException("Registration failed: Email already exists");
        }

        Auth auth = modelMapper.map(authDto, Auth.class);
        auth.setPassword(passenc.encode(authDto.getPassword()));
        auth.setRole(role);

        authRepository.save(auth);
        return "Registered successfully";
    }

    @Override
    public AuthResponseDTO update(int id, AuthRequestDTO authDto) {
    	Auth existing = authRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Auth not found with Id: " + id));
    	
    	if (authDto.getPassword() != null && !authDto.getPassword().trim().isEmpty()) {
    		existing.setPassword(passenc.encode(authDto.getPassword()));
    	}
    	
    	Auth updated = authRepository.save(existing);
    	return modelMapper.map(updated, AuthResponseDTO.class);
    }

    @Override
    public void delete(int id) {
        Auth existing = authRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auth not found with Id: " + id));
        authRepository.delete(existing);
    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        UserDetails userDetails = userInfoConfigManager.loadUserByUsername(loginDTO.getEmail());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setAccessToken(jwt);
        return loginResponse;
    }

	

	
}
