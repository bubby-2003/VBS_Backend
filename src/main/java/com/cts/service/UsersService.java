package com.cts.service;

import com.cts.dto.UsersRequestDTO;
import com.cts.dto.UsersResponseDTO;

public interface UsersService {
    UsersResponseDTO createProfile(UsersRequestDTO userDto);
    UsersResponseDTO updateProfile(int id, UsersRequestDTO updatedUserDto);
    UsersResponseDTO viewProfile(int id);
}
