package com.cts.service;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import java.util.List;
 
public interface AuthService {
    List<AuthResponseDTO> getAll();
    AuthResponseDTO getByEmail(String email);
    String create(AuthRequestDTO authDto);
    AuthResponseDTO update(String email, AuthRequestDTO authDto);
    void delete(String email);
    String login(AuthRequestDTO authDto);
}