package com.cts.service;

import com.cts.dto.UsersRequestDTO;
import com.cts.entity.Customer;

public interface UsersService {
    Customer createProfile(UsersRequestDTO userDto);
    Customer updateProfile(int id, UsersRequestDTO updatedUserDto);
//    Customer updateProfile(String email, UsersRequestDTO updatedUserDto);
    Customer viewProfile(int id);
//    Customer viewProfile(String email);
}
