package com.cts.service;
 
import com.cts.entity.Users;
 
public interface UsersService {
    Users createProfile(Users user);
    Users updateProfile(Integer id, Users updatedUser);
    Users viewProfile(String email);
}