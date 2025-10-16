package com.cts.service;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.dto.LoginDTO;
import com.cts.dto.LoginResponseDTO;

import jakarta.validation.Valid;

import java.util.List;

public interface AuthService {
    List<AuthResponseDTO> getAll();
    AuthResponseDTO getById(int id);
    String create(AuthRequestDTO authDto);
    void delete(int email);
    LoginResponseDTO login(LoginDTO loginDto);
	AuthResponseDTO update(int id, @Valid AuthRequestDTO authDto);
}
