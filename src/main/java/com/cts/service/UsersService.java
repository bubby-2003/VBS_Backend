package com.cts.service;

import com.cts.dto.UsersRequestDTO;
import com.cts.entity.Users;

public interface UsersService {
    Users createProfile(UsersRequestDTO userDto);
    Users updateProfile(String email, UsersRequestDTO updatedUserDto);
    Users viewProfile(String email);
}
