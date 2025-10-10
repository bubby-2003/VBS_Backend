package com.cts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.UsersRequestDTO;
import com.cts.entity.Auth;
import com.cts.entity.Users;
import com.cts.exception.ResourceNotFoundException;
import com.cts.repository.AuthRepository;
import com.cts.repository.UsersRepository;
import com.cts.service.UsersService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
	@Autowired
    private final UsersRepository usersRepository;
	@Autowired
    private final AuthRepository authRepository;

    @Override
    public Users createProfile(UsersRequestDTO userDto) {
        
        Auth existingAuth = authRepository.findById(userDto.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("Email not found in auth table: " + userDto.getEmail()));

      
        Users user = new Users();
        user.setAuth(existingAuth);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddress(userDto.getAddress());
        user.setPhone(userDto.getPhone());
        user.setStatus(userDto.getStatus());

        return usersRepository.save(user);
    }

    @Override
    public Users updateProfile(String email, UsersRequestDTO updatedUserDto) {
       
        Users existingUser = usersRepository.findByAuthEmail(email);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        existingUser.setFirstName(updatedUserDto.getFirstName());
        existingUser.setLastName(updatedUserDto.getLastName());
        existingUser.setAddress(updatedUserDto.getAddress());
        existingUser.setPhone(updatedUserDto.getPhone());
        existingUser.setStatus(updatedUserDto.getStatus());

        return usersRepository.save(existingUser);
    }

    @Override
    public Users viewProfile(String email) {
        Users user = usersRepository.findByAuthEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return user;
    }
}