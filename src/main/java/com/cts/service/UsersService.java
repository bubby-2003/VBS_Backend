package com.cts.service;
 
import com.cts.entity.Users;
 
public interface UsersService {
    Users createProfile(Users user);
    Users updateProfile(String email, Users updatedUser);
    Users viewProfile(String email);
}