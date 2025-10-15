package com.cts.service;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.dto.LoginDTO;
import com.cts.dto.LoginResponseDTO;

import java.util.List;

public interface AuthService {
    List<AuthResponseDTO> getAll();
    AuthResponseDTO getByEmail(String email);
    String create(AuthRequestDTO authDto);
    AuthResponseDTO update(String email, AuthRequestDTO authDto);
    void delete(String email);
    LoginResponseDTO login(LoginDTO loginDto);
}
