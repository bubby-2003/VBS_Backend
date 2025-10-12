package com.cts.mapper;

import com.cts.dto.AuthRequestDTO;
import com.cts.dto.AuthResponseDTO;
import com.cts.entity.Auth;

public class AuthMapper {
    public static AuthResponseDTO toDTO(Auth auth) {
        if (auth == null) {
            return null;
        }
        
        AuthResponseDTO dto = new AuthResponseDTO();
        
        dto.setEmail(auth.getEmail());
        dto.setRole(auth.getRole());
        return dto;
    }
    public static Auth toEntity(AuthRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Auth auth = new Auth();
        
        auth.setEmail(dto.getEmail());
        auth.setPassword(dto.getPassword()); 
        
        return auth;
    }
}